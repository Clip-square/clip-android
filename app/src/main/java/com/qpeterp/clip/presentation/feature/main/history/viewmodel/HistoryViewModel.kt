package com.qpeterp.clip.presentation.feature.main.history.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.data.data.meeting.MeetingData
import com.qpeterp.clip.domain.usecase.meeting.GetMeetingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryUiState(
    val isLoading: Boolean = false,
    val history: List<MeetingData>? = null,
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getMeetingListUseCase: GetMeetingListUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState

    init {
        getMeetingHistory()
    }

    private fun getMeetingHistory() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)  // 로딩 시작

            getMeetingListUseCase()
                .onSuccess {
                    if (it.isSuccessful) {
                        Log.d(
                            Constant.TAG,
                            "getMeetingHistory Success ${it.body()}"
                        )
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            history = it.body()!!.meetings
                        )
                    } else {
                        Log.d(
                            Constant.TAG,
                            "getMeetingHistory Failed EE : ${it.message()} ${it.code()}"
                        )
                    }
                }.onFailure {
                    Log.d(
                        Constant.TAG,
                        "getMeetingHistory Failed kkkkkk : ${it.message} ${it.cause}"
                    )
                }
        }
    }
}