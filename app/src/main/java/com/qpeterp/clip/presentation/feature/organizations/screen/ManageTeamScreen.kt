package com.qpeterp.clip.presentation.feature.organizations.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.clip.domain.model.team.Team
import com.qpeterp.clip.presentation.feature.organizations.viewmodel.ManageTeamViewModel
import com.qpeterp.clip.presentation.theme.Colors

@Composable
fun ManageTeamScreen(
    navController: NavController,
    viewModel: ManageTeamViewModel = hiltViewModel(),
) {
    val teamList = viewModel.teamList.collectAsState().value
    var expanded by remember { mutableStateOf(false) }
    var dialogState by remember { mutableStateOf(Pair(0, false)) }
    var dialogText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
//        viewModel.loadData()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 48.dp)
    ) {
        if (teamList.isEmpty()) {
            Text(
                text = "현재 생성한 조직이 없습니다.\n조직을 생성하여 회의를 시작하세요.",
                color = Colors.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(teamList) {
                    TeamCard(item = it)
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                onClick = { expanded = !expanded },
                contentColor = Colors.Black,
                containerColor = Colors.Black,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "icon to add team",
                    tint = Colors.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Colors.White,
                modifier = Modifier.align(Alignment.BottomEnd),
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "조직 참여하기",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Colors.Black
                        )
                    },
                    onClick = { dialogState = Pair(1, true) },
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "조직 만들기",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Colors.Black
                        )
                    },
                    onClick = { dialogState = Pair(2, true) },
                )
            }
        }
    }

    ManageTeamDialog(
        showDialog = dialogState.second,
        type = dialogState.first,
        currentText = dialogText,
        onValueChange = { dialogText = it },
        onDismiss = { dialogState = Pair(0, false) },
        onConfirm = {
            dialogState = Pair(0, false)
        }
    )
}

@Composable
private fun TeamCard(item: Team) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.White, RoundedCornerShape(6.dp))
            .border(3.dp, Colors.Black, RoundedCornerShape(6.dp))
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AccountTree,
            contentDescription = "icon to explain that text mean team",
            tint = Colors.Black,
            modifier = Modifier.size(42.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.teamName,
                color = Colors.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = "현재 저장된 조직원 : ${item.memberNum}",
                color = Colors.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun ManageTeamDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    type: Int,
    currentText: String = "",
    onValueChange: (String) -> Unit = {}, // 상태 변경을 처리할 콜백 추가
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Colors.White,
            titleContentColor = Colors.Black,
            shape = RoundedCornerShape(12.dp),
            title = {
                Text(
                    text = if (type == 1) "조직 코드 입력" else "조직 만들기",
                    color = Colors.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    if (type == 1) {
                        Text(
                            text = "팀장으로부터 조직 코드\n를 받아 여기에 입력하세요.",
                            color = Colors.DarkGray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    OutlinedTextField(
                        value = currentText,
                        onValueChange = { onValueChange(it) },
                        label = { Text(if (type == 1) "조직 코드" else "조직 이름") }
                    )
                }
            },
            confirmButton = {
                Text(
                    text = if (type == 1) "참여하기" else "만들기",
                    color = Colors.Black,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onConfirm() }
                        .padding(horizontal = 8.dp)
                )
            },
            dismissButton = {
                Text(
                    text = "취소",
                    color = Colors.DarkGray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(horizontal = 8.dp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageTeamScreenPreView() {
    val context = LocalContext.current
    ManageTeamScreen(
        navController = NavController(context),
        viewModel = ManageTeamViewModel()
    )
}