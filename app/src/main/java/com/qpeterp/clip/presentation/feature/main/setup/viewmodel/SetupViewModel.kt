package com.qpeterp.clip.presentation.feature.main.setup.viewmodel

import androidx.lifecycle.ViewModel
import com.qpeterp.clip.domain.model.meeting.SubTopic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor() : ViewModel() {
    private val _meetingSubTopicList = MutableStateFlow(listOf(SubTopic(1, "")))
    val meetingSubTopicList: StateFlow<List<SubTopic>> get() = _meetingSubTopicList

    fun addSubTopic(subTopic: SubTopic) {
        _meetingSubTopicList.value += subTopic
    }

    fun updateSubTopicText(subTopicNumber: Int, newContent: String) {
        _meetingSubTopicList.value = _meetingSubTopicList.value.map { subTopic ->
            if (subTopic.numbering == subTopicNumber) subTopic.copy(content = newContent) else subTopic
        }
    }

    fun removeSubTopic(subTopicNumber: Int) {
        _meetingSubTopicList.value = _meetingSubTopicList.value.filterNot { it.numbering == subTopicNumber }
    }
}