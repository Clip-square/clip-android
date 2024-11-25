package com.qpeterp.clip.presentation.feature.main.history.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    val dummyList = listOf(
        AA("2024-11-22", "이성은 그는 신이야.", "3:44"),
        AA("2024-11-22", "이성은 그는 신이야.", "3:44"),
        AA("2024-11-22", "이성은 그는 신이야.", "3:44"),
        AA("2024-11-22", "이성은 그는 신이야.", "3:44"),
        AA("2024-11-22", "이성은 그는 신이야.", "3:44"),
        AA("2024-11-22", "이성은 그는 신이야.", "3:44"),
    )
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        items(dummyList) {
            MeetingHistoryCard(
                date = it.date,
                topic = it.topic,
                time = it.time
            ) {

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