package com.qpeterp.clip.presentation.feature.end.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.qpeterp.clip.presentation.core.ClipButton
import com.qpeterp.clip.presentation.feature.end.viewmodel.EndViewModel
import com.qpeterp.clip.presentation.theme.Colors

@Composable
fun EndScreen(
    navController: NavController,
    viewModel: EndViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "어쩌구 저쩌구 에 대한",
                color = Colors.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "회의가 종료되었습니다.",
                color = Colors.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "정리된 회의록",
                color = Colors.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                ClipButton(
                    text = "Notion으로 다운로드",
                    textColor = Colors.White,
                    backgroundColor = Colors.Black,
                    modifier = Modifier.weight(1f)
                ) {

                }
                ClipButton(
                    text = "PDF로 다운로드",
                    textColor = Colors.White,
                    backgroundColor = Colors.Black,
                    modifier = Modifier.weight(1f)
                ) {

                }
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "회의 시간",
                color = Colors.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "1시간 30분 27초",
                color = Colors.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "회의 참여도",
                color = Colors.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            MyBarChartParent()
        }
    }
}

@Composable
fun MyBarChartParent() {
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