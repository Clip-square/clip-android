package com.qpeterp.clip.presentation.feature.end.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.qpeterp.clip.presentation.core.ClipButton
import com.qpeterp.clip.presentation.feature.meeting.viewmodel.MeetingViewModel
import com.qpeterp.clip.presentation.root.navigation.NavGroup
import com.qpeterp.clip.presentation.theme.Colors

@Composable
fun EndScreen(
    navController: NavController,
    viewModel: MeetingViewModel = hiltViewModel(),
) {
    val title = viewModel.meetingData.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "${title!!.title}에 대한",
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

            ClipButton(
                text = "저장된 회의록 보러가기",
                modifier = Modifier.fillMaxWidth()
            ) {
//                viewModel.resetViewModel()
                navController.navigate(NavGroup.Main.MAIN) {
                    popUpTo(0)
                }
            }
        }
    }
}