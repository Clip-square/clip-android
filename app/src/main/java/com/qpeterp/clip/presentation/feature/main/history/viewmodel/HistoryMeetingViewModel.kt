package com.qpeterp.clip.presentation.feature.main.history.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.data.data.meeting.MeetingData
import com.qpeterp.clip.domain.usecase.meeting.GetMeetingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryMeetingUiState(
    val isLoading: Boolean = false,
    val history: MeetingData? = null,
)

@HiltViewModel
class HistoryMeetingViewModel @Inject constructor(
    private val getMeetingUseCase: GetMeetingUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryMeetingUiState())
    val uiState: StateFlow<HistoryMeetingUiState> = _uiState

    private var meetingId = ""

    fun setId(
        id: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        meetingId = id
        getMeetingHistory(
            onSuccess = {
                onSuccess()
                _uiState.value = _uiState.value.copy(isLoading = false, history = it)
            },
            onFailed = {
                onFailed()
                Log.d(Constant.TAG, "FuckFcukFuckFcukFuckFcukFuckFcuk")
            }
        )
    }

    private fun getMeetingHistory(
        onSuccess: (MeetingData) -> Unit,
        onFailed: () -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)  // 로딩 시작
            getMeetingUseCase(
                meetingId = meetingId
            ).onSuccess {
                if (it.isSuccessful) {
                    Log.d(
                        Constant.TAG,
                        "HistoryMeetingViewModel getMeetingHistory success : ${it.body()}"
                    )
                    onSuccess(it.body()!!)
                } else {
                    Log.d(
                        Constant.TAG,
                        "HistoryMeetingViewModel getMeetingHistory Failed 4 : ${it.message()} ${it.code()}"
                    )
                    onFailed()
                }
            }.onFailure {
                Log.d(
                    Constant.TAG,
                    "HistoryMeetingViewModel getMeetingHistory Failed : ${it.cause} ${it.message}"
                )
                onFailed()
            }
        }
    }
}