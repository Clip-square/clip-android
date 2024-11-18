@file:OptIn(ExperimentalMaterial3Api::class)

package com.qpeterp.clip.presentation.feature.main.setup.screen
import android.util.Log
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

@Composable
fun SetupScreen(
    navController: NavController,
    viewModel: SetupViewModel = hiltViewModel(),
) {
    var meetingTopic by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var isRecoding by remember { mutableStateOf(true) }
    var showDeleteSubTopicDialogState by remember { mutableStateOf(Pair(false, 0)) }

    val saveButtonColor by animateColorAsState(
        targetValue = if (isRecoding) Colors.Black else Colors.LightGray, label = ""
    )
    val saveTextColor by animateColorAsState(
        targetValue = if (isRecoding) Colors.White else Colors.Black, label = ""
    )
    val discardButtonColor by animateColorAsState(
        targetValue = if (!isRecoding) Colors.Black else Colors.LightGray, label = ""
    )
    val discardTextColor by animateColorAsState(
        targetValue = if (!isRecoding) Colors.White else Colors.Black, label = ""
    )

    val subTopicList by viewModel.meetingSubTopicList.collectAsState()


    Box(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 72.dp)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(35.dp),
            modifier = Modifier
                .fillMaxWidth()
//                .verticalScroll(scrollState)
                .align(Alignment.TopCenter)
        ) {
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "회의 목차",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    color = Colors.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(subTopicList) {
                        ClipTextField(
                            label = it.numbering.toString(),
                            idValue = true,
                            currentText = meetingTopic,
                            onDelete = { showDeleteSubTopicDialogState = Pair(true, it) },
                            idValueChange = { numbering, content ->
                                viewModel.updateSubTopicText(numbering, content)
                            }
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "icon to add sub topic",
                    tint = Colors.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            viewModel.addSubTopic(
                                SubTopic(subTopicList.size + 1, "")
                            )
                        }
                )
            }

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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "음성 파일 저장 여부",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ClipButton(
                        text = "저장 안함",
                        textColor = discardTextColor,
                        backgroundColor = discardButtonColor,
                        modifier = Modifier.weight(1f)
                    ) {
                        isRecoding = false
                    }
                    ClipButton(
                        text = "저장",
                        textColor = saveTextColor,
                        backgroundColor = saveButtonColor,
                        modifier = Modifier.weight(1f)
                    ) {
                        isRecoding = true
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