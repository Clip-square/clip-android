package com.qpeterp.clip.presentation.feature.main.history.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.qpeterp.clip.presentation.feature.main.history.viewmodel.HistoryMeetingViewModel
import com.qpeterp.clip.presentation.theme.Colors
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun HistoryMeetingScreen(
    navController: NavController,
    id: String?,
    viewModel: HistoryMeetingViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val scrollState = rememberScrollState()

    if (id != null) {
        viewModel.setId(
            id,
            onSuccess = {},
            onFailed = {}
        )
    }

    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else {
        val meetingData = uiState.history
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 64.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "회의 시간",
                color = Colors.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = meetingData?.totalDuration ?: "회의 시간을 불러오지 못했습니다.",
                color = Colors.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text(
                text = "요약된 회의록",
                color = Colors.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            MarkdownText(
                markdown = meetingData?.meetingMinutes ?: "요약된 회의록을 불러오지 못했습니다.",
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 64.dp)
                    .fillMaxWidth()
                    .background(Colors.LightGray, RoundedCornerShape(6.dp))
                    .padding(20.dp)
            )
//            Text(
//                text = "회의 참여도",
//                color = Colors.Black,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.SemiBold
//            )
//            MyBarChartParent(
//
//            )
        }
    }
}

@Composable
fun MyBarChartParent(

) {
    BarChart(
        barChartData = BarChartData(
            bars = listOf(
                BarChartData.Bar(
                    label = "이성은",
                    value = 100f,
                    color = Colors.Black
                ),
                BarChartData.Bar(
                    label = "서승훈",
                    value = 20f,
                    color = Colors.Black
                ),
                BarChartData.Bar(
                    label = "김호준",
                    value = 1f,
                    color = Colors.Black
                )
            ),
        ),
        // Optional properties.
        modifier = Modifier.fillMaxSize(),
        animation = simpleChartAnimation(),
        barDrawer = SimpleBarDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        labelDrawer = SimpleValueDrawer(
            SimpleValueDrawer.DrawLocation.XAxis
        ),
    )
}