package com.qpeterp.clip.presentation.feature.auth.register.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.clip.presentation.core.ClipAuthTextField
import com.qpeterp.clip.presentation.core.ClipButton
import com.qpeterp.clip.presentation.core.ClipDialog
import com.qpeterp.clip.presentation.theme.Colors
import com.qpeterp.clip.presentation.feature.auth.register.viewmodel.RegisterViewModel

@Composable
fun RegisterEmailScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val email = remember { mutableStateOf(viewModel.id) }
    val errorMessage = remember { mutableStateOf("") }
    HandleBack(
        viewModel,
        navController
    )

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
                modifier = Modifier.padding(top = 80.dp)
            ) {
                Text(
                    "이메일",
                    color = Colors.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "을 입력해주세요.",
                    color = Colors.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                "로그인 시, 사용될 이메일입니다.",
                color = Colors.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Column {
                ClipAuthTextField(
                    label = "이메일",
                    currentText = email.value,
                    keyboardType = KeyboardType.Text,
                    onValueChange = { email.value = it } // 상태 변경 처리
                )

                if (errorMessage.value.isNotEmpty()) {
                    Text(
                        text = errorMessage.value,
                        color = Colors.Red,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }

        Column {
            ClipButton(
                text = "다음으로",
                onClick = {
                    if (email.value.isEmpty()) {
                        errorMessage.value = "이메일을 입력해주세요."
                        return@ClipButton
                    } else if (!isValidEmail(email.value)) {
                        errorMessage.value = "유효하지 않은 이메일 형식입니다."
                        return@ClipButton
                    }

                    viewModel.inputId(email.value)
                    navController.navigate("registerPassword")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    return emailRegex.matches(email)
}

@Composable
fun HandleBack(
    viewModel: RegisterViewModel,
    navController: NavController
) {
    val showDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showDialog.value = true
    }

    ClipDialog(
        showDialog = showDialog.value,
        onDismiss = { showDialog.value = false },
        onConfirm = {
            showDialog.value = false
            viewModel.clear() // Clear the view model data
            navController.popBackStack() // Navigate back
        },
        title = "회원가입 중단",
        message = "정말 로그인 페이지로 돌아가시겠습니까?\n작성 중인 정보가 사라집니다.",
    )
}