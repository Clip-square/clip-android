package com.qpeterp.clip.presentation.root.main.view

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.qpeterp.clip.application.ClipApplication
import com.qpeterp.clip.application.PreferenceManager
import com.qpeterp.clip.presentation.root.navigation.NavigationGraph
import com.qpeterp.clip.presentation.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var showPermissionDialog = false
    private val multiplePermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var allPermissionsGranted = true // 모든 권한 승인 여부를 저장하는 변수

        permissions.entries.forEach { (permission, isGranted) ->
            when {
                isGranted -> {
                    // 권한이 승인된 경우 처리할 작업
                    Log.d("Permissions", "$permission granted.")
                }

                !isGranted -> {
                    // 권한이 거부된 경우 처리할 작업
                    allPermissionsGranted = false // 하나라도 거부되면 false로 설정
                    Log.d("Permissions", "$permission denied.")
                }

                else -> {
                    // 사용자가 "다시 묻지 않음"을 선택한 경우 처리할 작업
                    Log.d("Permissions", "$permission denied with 'Don't ask again'.")
                }
            }
        }

        if (!allPermissionsGranted) {
            showPermissionDialog = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()

        enableEdgeToEdge()
        ClipApplication.prefs = PreferenceManager(application)
        val prefs = ClipApplication.prefs

        val isLoggedIn = prefs.token.isNotEmpty()

        setContent {
            MyAppTheme {
                MyApp(isLoggedIn)
            }
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
        multiplePermissionsLauncher.launch(permissions)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(isLoggedIn: Boolean) {
    val navController = rememberNavController()
    NavigationGraph(navController = navController, isUserLoggedIn = isLoggedIn)
}
