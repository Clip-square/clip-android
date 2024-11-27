package com.qpeterp.clip.presentation.feature.main.history.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.clip.presentation.feature.main.history.viewmodel.HistoryViewModel
import com.qpeterp.clip.presentation.theme.Colors

data class AA(
    val date: String,
    val topic: String,
    val time: String,
)

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value

    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else {
        val meetingHistoryList = viewModel.uiState.value.history!!
        if (meetingHistoryList.isEmpty()) {
            Text(
                text = "회의 기록이 없습니다.",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = Colors.DarkGray,
                textAlign = TextAlign.Center, // 중앙 정렬 추가 (세로엔 영향 없음)
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center) // 텍스트를 중앙에 배치
            )
        } else {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                items(meetingHistoryList) {
                    MeetingHistoryCard(
                        date = it.createdAt,
                        topic = it.title,
                        time = it.totalDuration.toString()
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
private fun MeetingHistoryCard(
    date: String,
    topic: String,
    time: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Colors.LightGray, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = date,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Colors.Black
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = topic,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.Black
            )
            Text(
                text = time,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.DarkGray
            )
        }
    }
}