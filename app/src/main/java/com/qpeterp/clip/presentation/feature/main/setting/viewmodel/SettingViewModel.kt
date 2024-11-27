package com.qpeterp.clip.presentation.feature.main.setting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    fun logout(
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        viewModelScope.launch {
            logoutUseCase()
                .onSuccess {
                    if (it.isSuccessful) {
                        onSuccess()
                    } else {
                        Log.e(Constant.TAG, "로그아웃 실패 $it.message()}")
                        onFailed()
                    }
                }.onFailure {
                    onFailed()
                }
        }
    }
}