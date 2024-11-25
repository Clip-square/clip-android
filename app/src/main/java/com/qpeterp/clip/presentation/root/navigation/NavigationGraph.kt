package com.qpeterp.clip.presentation.root.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.qpeterp.clip.presentation.feature.auth.register.screen.RegisterCompleteScreen
import com.qpeterp.clip.presentation.feature.auth.register.screen.RegisterEmailScreen
import com.qpeterp.clip.presentation.feature.auth.register.screen.RegisterNameScreen
import com.qpeterp.clip.presentation.feature.auth.register.screen.RegisterPasswordScreen
import com.qpeterp.clip.presentation.feature.end.screen.EndScreen
import com.qpeterp.clip.presentation.feature.meeting.screen.MeetingScreen
import com.qpeterp.clip.presentation.feature.main.setup.screen.SetupScreen
import com.qpeterp.clip.presentation.feature.auth.login.screen.LoginScreen
import com.qpeterp.clip.presentation.feature.auth.register.viewmodel.RegisterViewModel
import com.qpeterp.clip.presentation.feature.main.history.screen.HistoryScreen
import com.qpeterp.clip.presentation.feature.main.screen.MainScreen
import com.qpeterp.clip.presentation.feature.main.setting.screen.SettingScreen
import com.qpeterp.clip.presentation.feature.team.screen.ManageTeamScreen

@ExperimentalMaterial3Api
@Composable
fun NavigationGraph(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
) {
    val registerViewModel: RegisterViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn) NavGroup.Main.MAIN else NavGroup.Auth.LOGIN
    ) {
        // Auth 그룹 네비게이션
        composable(NavGroup.Auth.LOGIN) {
            LoginScreen(navController)
        }

        composable(NavGroup.Auth.REGISTER_ID) {
            RegisterEmailScreen(navController, registerViewModel)
        }

        composable(NavGroup.Auth.REGISTER_PASSWORD) {
            RegisterPasswordScreen(navController, registerViewModel)
        }

        composable(NavGroup.Auth.REGISTER_NAME) {
            RegisterNameScreen(navController, registerViewModel)
        }

        composable(NavGroup.Auth.REGISTER_COMPLETE) {
            RegisterCompleteScreen(navController)
        }

        // Main 그룹 네비게이션
        composable(NavGroup.Main.MAIN) {
            MainScreen(navController)
        }

        composable(NavGroup.Main.MEETING_HISTORY) {
            HistoryScreen(navController)
        }

        composable(NavGroup.Main.SETUP) {
            SetupScreen(navController)
        }

        composable(NavGroup.Main.SETTING) {
            SettingScreen(navController)
        }

        // Feature 그룹 네비게이션
        composable(NavGroup.Features.RECODE) {
        }

        composable(NavGroup.Features.MANAGE_TEAM) {
            ManageTeamScreen(navController)
        }

        composable(NavGroup.Features.MEETING) {
            MeetingScreen(navController)
        }

        composable(NavGroup.Features.END) {
            EndScreen(navController)
        }
    }
}