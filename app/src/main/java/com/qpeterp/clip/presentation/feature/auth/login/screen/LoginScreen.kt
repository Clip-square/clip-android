package com.qpeterp.clip.presentation.feature.auth.login.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.presentation.core.ClipAuthTextField
import com.qpeterp.clip.presentation.core.ClipButton
import com.qpeterp.clip.presentation.extensions.shortToast
import com.qpeterp.clip.presentation.feature.auth.login.viewmodel.LoginViewModel
import com.qpeterp.clip.presentation.theme.Colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    val lastBackPressTime = remember { mutableLongStateOf(0L) } // 마지막 클릭 시간 추적

    BackHandler(enabled = true) {
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastBackPressTime.longValue < 2000) {
            exitProcess(0)
        } else {
            context.shortToast("'뒤로' 버튼을 한번 더 누르시면 종료됩니다.")
            lastBackPressTime.longValue = currentTime
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.White)
            .padding(horizontal = 20.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 80.dp)
            ) {
                Text(
                    "Clip",
                    color = Colors.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "에 로그인하기",
                    color = Colors.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                "로그인할 아이디와 비밀번호를 입력해주세요",
                color = Colors.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Column {
                ClipAuthTextField(
                    label = "이메일",
                    keyboardType = KeyboardType.Text,
                    currentText = "test@test.com",
                    onValueChange = { email = it } // 상태 변경 처리
                )

                Spacer(modifier = Modifier.height(32.dp))

                ClipAuthTextField(
                    label = "비밀번호",
                    keyboardType = KeyboardType.Password,
                    currentText = "asdfasdf1!",
                    onValueChange = { password = it } // 상태 변경 처리
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Colors.Red,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }

        Column {
            ClipButton(
                text = "로그인",
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        errorMessage = "빈값을 채워주세요."
                        return@ClipButton
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.login(email, password,
                            onLoginSuccess = {
//                                navController.navigate("main") {
//                                    Log.d(Constant.TAG, "로그인 성공")
//                                    popUpTo(0)
//                                }
                                navController.navigate("manageTeam") {
                                    Log.d(Constant.TAG, "로그인 성공")
                                    popUpTo(0)
                                }
                            },
                            onLoginFailure = { message ->
                                errorMessage = message
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "계정이 없으신가요? ",
                    color = Colors.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "회원가입",
                    color = Colors.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        navController.navigate("registerId")
                    }
                )
            }
        }
    }
}