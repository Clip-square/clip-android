package com.qpeterp.clip.presentation.feature.meeting.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.rounded.RecordVoiceOver
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.data.data.meeting.Section
import com.qpeterp.clip.presentation.core.ClipButton
import com.qpeterp.clip.presentation.feature.meeting.viewmodel.MeetingViewModel
import com.qpeterp.clip.presentation.theme.Colors
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun MeetingScreen(
    navController: NavController,
    viewModel: MeetingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    // startRecode 호출을 LaunchedEffect로 실행
    LaunchedEffect(Unit) {
        viewModel.startRecode(
            context,
            onSuccess = {
                navController.navigate("end") {
                    popUpTo(0)
                }
            },
            onFailed = {
                Log.d(Constant.TAG, "음성 박음박음박음")
            }
        )
    }
    val meetingState = viewModel.meetingState.collectAsState().value

    val black = if (meetingState) Colors.Black else Colors.White
    val white = if (meetingState) Colors.White else Colors.Black

    val meetingData = viewModel.meetingData.collectAsState().value
    val sections = meetingData?.sections ?: listOf(Section("ㅁㄴㅇㄹㅁㄴㅇㄹ", ""))
    val currentSubTopicIndex = viewModel.subTopicIndex.collectAsState().value

    Box(
        modifier = Modifier
            .background(black)
            .padding(horizontal = 30.dp, vertical = 72.dp)
            .fillMaxSize()
    ) {
        if (meetingData == null) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(58.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "남은 회의 시간",
                        color = white,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    TimerScreen(
                        initialTime = meetingData.totalDuration,
                        initialTextColor = white,
                        isPaused = !meetingState,
                        viewModel = viewModel
                    )
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 42.dp)
                ) {
                    Text(
                        text = "회의 주제",
                        color = white,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = meetingData.title,
                        textAlign = TextAlign.Center,
                        lineHeight = TextUnit(1.2f, TextUnitType.Em),
                        color = white,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(sections) { index, item ->
                        MeetingSubTopicCard(
                            subTopic = item.name,
                            subTopicIndex = index,
                            black = black,
                            white = white,
                            isLastIndex = (index == sections.size - 1),
                            isCurrentIndex = currentSubTopicIndex == index
                        ) {
                            val endTime =
                                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                                    Date()
                                )
                            viewModel.addSectionsEndTime(endTime)
                            if (index == sections.size - 1) {
                                viewModel.stopRecode()
                                return@MeetingSubTopicCard
                            }
                            viewModel.updateSubTopicIndex(index + 1)
                        }
                    }
                }
            }
        }

        ClipIconButton(
            text = if (meetingState) "휴식하기" else "회의 재개하기",
            textColor = black,
            icon = if (meetingState) Icons.Default.Coffee else Icons.Rounded.RecordVoiceOver,
            iconColor = black,
            backgroundColor = white,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            if (meetingState) {
                viewModel.pauseRecode()
            } else {
                viewModel.resumeRecode()
            }
            viewModel.changeMeetingState()
        }
    }
}

@Composable
private fun ClipIconButton(
    text: String,
    textColor: Color = Colors.White, // 기본 텍스트 색
    icon: ImageVector,
    iconColor: Color,
    backgroundColor: Color = Colors.Black,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp) // 모서리 둥글게
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 32.dp), // 내부 여백
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "icon to rest",
            modifier = Modifier.size(24.dp),
            tint = iconColor
        )
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MeetingSubTopicCard(
    subTopicIndex: Int,
    isCurrentIndex: Boolean,
    subTopic: String,
    black: Color,
    white: Color,
    isLastIndex: Boolean,
    onButtonClick: () -> Unit,
) {
    if (isCurrentIndex) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(bottom = 18.dp, end = 18.dp)
                    .background(white, RoundedCornerShape(6.dp))
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 30.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = "회의 소주제",
                    color = black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = subTopic,
                    textAlign = TextAlign.Center,
                    lineHeight = TextUnit(1.2f, TextUnitType.Em),
                    color = black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            ClipButton(
                modifier = Modifier
                    .border(3.dp, white, RoundedCornerShape(6.dp))
                    .align(Alignment.BottomEnd),
                text = if (isLastIndex) "회의 끝내기" else "다음 소주제",
                textColor = white,
                backgroundColor = black
            ) { onButtonClick() }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.DarkGray, RoundedCornerShape(6.dp))
                .padding(10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "제 $subTopicIndex 소주제",
                color = Colors.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = subTopic,
                color = Colors.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TimerScreen(
    initialTime: String,
    initialTextColor: Color = Color.Black,
    expiredTextColor: Color = Color.Red,
    isPaused: Boolean,
    viewModel: MeetingViewModel,
) {
    // initialTime을 초 단위로 파싱
    var remainingTime by remember { mutableIntStateOf(parseTimeToSeconds(initialTime)) }
    var isExpired by remember { mutableStateOf(false) }
    val startTimeInMillis = remember { System.currentTimeMillis() }  // 타이머 시작 시간 (밀리초 단위)

    // 타이머가 진행되는 동안 1초마다 업데이트
    LaunchedEffect(remainingTime, isPaused) {
        if (!isPaused) { // 일시정지 상태가 아닐 때만 실행
            if (remainingTime > 0) {
                kotlinx.coroutines.delay(1000L) // 1초마다 업데이트
                remainingTime -= 1
            } else {
                isExpired = true
                remainingTime -= 1 // 음수로 증가 (시간이 0 이하로 떨어지면 음수로 유지)
            }
        }
    }

    // 남은 시간 포맷
    val formattedTime = formatSecondsToTime(remainingTime)
    // 경과된 시간 계산 (초 단위)
    viewModel.setTotalDuration(getElapsedTime(startTimeInMillis))

    // 타이머 UI
    Text(
        text = formattedTime,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        color = if (isExpired) expiredTextColor else initialTextColor
    )
}

// 초를 시:분:초 형식으로 변환
fun formatSecondsToTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}

// 시작 시간부터 경과된 시간을 계산
fun getElapsedTime(startTimeInMillis: Long): String {
    val currentTimeInMillis = System.currentTimeMillis()
    val elapsedTimeInMillis = currentTimeInMillis - startTimeInMillis
    return formatMillisToTime(elapsedTimeInMillis)
}

// 밀리초를 시:분:초 형식으로 변환
fun formatMillisToTime(millis: Long): String {
    val hours = (millis / 3600000) % 24
    val minutes = (millis / 60000) % 60
    val seconds = (millis / 1000) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

// "HH:mm:ss" 형식의 시간 문자열을 초로 변환
fun parseTimeToSeconds(timeString: String): Int {
    val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val date = format.parse(timeString) ?: return 0
    val calendar = Calendar.getInstance()
    calendar.time = date

    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    val minutes = calendar.get(Calendar.MINUTE)
    val seconds = calendar.get(Calendar.SECOND)

    return hours * 3600 + minutes * 60 + seconds
}