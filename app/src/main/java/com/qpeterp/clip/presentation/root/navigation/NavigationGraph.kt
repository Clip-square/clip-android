package com.qpeterp.clip.presentation.root.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.qpeterp.clip.presentation.feature.main.end.screen.EndScreen
import com.qpeterp.clip.presentation.feature.main.meeting.screen.MeetingScreen
import com.qpeterp.clip.presentation.feature.main.setup.screen.SetupScreen

@ExperimentalMaterial3Api
@Composable
fun NavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavGroup.Main.SETUP
    ) {
        // Main 그룹 네비게이션
        composable(NavGroup.Main.SETUP) {
            SetupScreen(navController)
        }

        composable(NavGroup.Main.MEETING) {
            MeetingScreen(navController)
        }

        composable(NavGroup.Main.END) {
            EndScreen(navController)
        }
    }
}