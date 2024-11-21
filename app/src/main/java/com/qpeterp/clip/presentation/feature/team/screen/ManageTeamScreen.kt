package com.qpeterp.clip.presentation.feature.team.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.qpeterp.clip.presentation.feature.team.viewmodel.ManageTeamViewModel
import com.qpeterp.clip.presentation.theme.Colors

@Composable
fun ManageTeamScreen(
    navController: NavController,
    viewModel: ManageTeamViewModel = hiltViewModel()
) {
    val teamList = viewModel.teamList.collectAsState().value

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

        FloatingActionButton(
            onClick = {},
            contentColor = Colors.Black,
            containerColor = Colors.Black,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "icon to add team",
                tint = Colors.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
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

@Preview(showBackground = true)
@Composable
private fun ManageTeamScreenPreView() {
    val context = LocalContext.current
    ManageTeamScreen(
        navController = NavController(context),
        viewModel = ManageTeamViewModel()
    )
}