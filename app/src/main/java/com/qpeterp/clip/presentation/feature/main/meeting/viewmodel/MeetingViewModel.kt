package com.qpeterp.clip.presentation.feature.main.meeting.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MeetingViewModel @Inject constructor() : ViewModel() {
    private val _meetingState = MutableStateFlow(true)
    val meetingState: StateFlow<Boolean> get() = _meetingState

    private val _subTopicIndex = MutableStateFlow(0)
    val subTopicIndex: StateFlow<Int> get() = _subTopicIndex

    fun changeMeetingState() {
        _meetingState.value = !_meetingState.value
    }

    fun updateSubTopicIndex(index: Int) {
        _subTopicIndex.value = index
    }
}