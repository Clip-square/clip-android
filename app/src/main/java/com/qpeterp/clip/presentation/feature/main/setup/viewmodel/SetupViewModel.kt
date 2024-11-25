package com.qpeterp.clip.presentation.feature.main.setup.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor() : ViewModel() {
    private val _meetingSubTopicList = MutableStateFlow(listOf(""))
    val meetingSubTopicList: StateFlow<List<String>> get() = _meetingSubTopicList

    fun addSubTopic() {
        _meetingSubTopicList.value += ""
    }

    fun updateSubTopicText(subTopicIndex: Int, newContent: String) {
        _meetingSubTopicList.value = _meetingSubTopicList.value.mapIndexed { index, subTopic ->
            if (index == subTopicIndex) newContent else subTopic
        }
    }

    fun removeSubTopic(subTopicIndex: Int) {
        _meetingSubTopicList.value = _meetingSubTopicList.value.filterIndexed { index, _ -> index != subTopicIndex } // 지정된 인덱스 삭제
    }
}