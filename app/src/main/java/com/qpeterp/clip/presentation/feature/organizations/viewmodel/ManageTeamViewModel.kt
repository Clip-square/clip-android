package com.qpeterp.clip.presentation.feature.organizations.viewmodel

import androidx.lifecycle.ViewModel
import com.qpeterp.clip.domain.model.team.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ManageTeamViewModel @Inject constructor(
) : ViewModel() {
    private val _teamList = MutableStateFlow<List<Team>>(listOf(Team("clip", 4)))
    val teamList: StateFlow<List<Team>> get() = _teamList
}