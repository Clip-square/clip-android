package com.qpeterp.clip.presentation.feature.auth.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    suspend fun login(
        email: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        Log.d(Constant.TAG, "login info : email: $email, password: $password")
        val result = loginUseCase(
            param = LoginUseCase.Param(
                email = email,
                password = password
            )
        )
        result.onSuccess { response ->
            if (response.isSuccessful) {
                Log.d(Constant.TAG, "로그인 성공")
                onLoginSuccess()
            } else {
                Log.e(Constant.TAG, "Login failed e: ${response.message()}")
                onLoginFailure(response.message())
            }

        }.onFailure {
            Log.e(Constant.TAG, "LoginViewModel error : $it")
            onLoginFailure(it.message.toString())
        }
    }
}