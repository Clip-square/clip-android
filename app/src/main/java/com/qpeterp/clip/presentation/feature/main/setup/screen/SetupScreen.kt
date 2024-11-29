@file:OptIn(ExperimentalMaterial3Api::class)

package com.qpeterp.clip.presentation.feature.main.setup.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.presentation.core.ClipButton
import com.qpeterp.clip.presentation.core.ClipDialog
import com.qpeterp.clip.presentation.core.ClipTextField
import com.qpeterp.clip.presentation.feature.main.setup.viewmodel.SetupViewModel
import com.qpeterp.clip.presentation.theme.Colors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SetupScreen(
    navController: NavController,
    viewModel: SetupViewModel = hiltViewModel(),
) {
    var meetingTopic by remember { mutableStateOf("") }
    var showDeleteSubTopicDialogState by remember { mutableStateOf(Pair(false, 0)) }
    val subTopicList by viewModel.meetingSubTopicList.collectAsState()

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 72.dp)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(35.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(bottom = 72.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "회의 주제",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Colors.Black
                        )
                        ClipTextField(
                            label = "회의 주제 입력",
                            currentText = meetingTopic,
                        ) {
                            meetingTopic = it
                        }
                    }
                }

                item {
                    Text(
                        text = "회의 목차",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        color = Colors.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                itemsIndexed(subTopicList) { index, subTopic ->
                    ClipTextField(
                        label = index.toString(),
                        idValue = true,
                        currentText = subTopic,
                        onDelete = { showDeleteSubTopicDialogState = Pair(true, index) },
                        idValueChange = { _, content ->
                            viewModel.updateSubTopicText(index, content)
                        }
                    )
                }

                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "icon to add sub topic",
                            tint = Colors.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopCenter)
                                .clickable {
                                    viewModel.addSubTopic()
                                }
                        )
                    }

                }

                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "회의 시간",
                            textAlign = TextAlign.Left,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        WheelTimePicker { snappedTime ->
                            Log.d(Constant.TAG, "snappedTime is $snappedTime")
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val items = List(20) { "문진위" }

                        Text(
                            text = "회의 참여 인원",
                            textAlign = TextAlign.Left,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                        FlowRow(
                            maxItemsInEachRow = 5, // 수평 간격
                            maxLines = 120, // 수직 간격
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items.forEach { item ->
                                Box(
                                    modifier = Modifier
                                        .background(Colors.White, shape = RoundedCornerShape(100.dp))
                                        .border(2.dp, Colors.Black, RoundedCornerShape(100.dp))
                                        .padding(horizontal = 12.dp, vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        ClipButton(
            text = "회의 시작하기",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            navController.navigate("meeting") {
                popUpTo(0)
            }
        }
    }

    ClipDialog(
        showDialog = showDeleteSubTopicDialogState.first,
        title = "목차 삭제",
        message = "정말 목차를 삭제하시겠습니까?",
        onDismiss = { showDeleteSubTopicDialogState = Pair(false, 0) },
        onConfirm = {
            viewModel.removeSubTopic(showDeleteSubTopicDialogState.second)
            showDeleteSubTopicDialogState = Pair(false, 0)
        }
    )
}