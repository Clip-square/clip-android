package com.qpeterp.clip.presentation.feature.organizations.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.data.data.organization.Organizations
import com.qpeterp.clip.domain.usecase.organization.CreateOrganizationUserCase
import com.qpeterp.clip.domain.usecase.organization.GetOrganizationListUseCase
import com.qpeterp.clip.domain.usecase.organization.GetOrganizationUseCase
import com.qpeterp.clip.domain.usecase.organization.JoinOrganizationUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageTeamViewModel @Inject constructor(
    private val getOrganizationListUseCase: GetOrganizationListUseCase,
    private val createOrganizationUserCase: CreateOrganizationUserCase,
    private val joinOrganizationUserCase: JoinOrganizationUserCase,
    private val getOrganizationUseCase: GetOrganizationUseCase
) : ViewModel() {

    private val _teamList = MutableStateFlow<List<Organizations>>(emptyList())
    val teamList: StateFlow<List<Organizations>> get() = _teamList

    // 로딩 상태 추가
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        loadTeams()
    }

    private fun loadTeams() {
        viewModelScope.launch {
            _isLoading.value = true // 로딩 시작
            val result = getOrganizationListUseCase()

            result.onSuccess { response ->
                if (response.isSuccessful) {
                    _teamList.value = response.body()!!
                    Log.d(Constant.TAG, "ManageTeamViewModel response : ${response.body()}")
                } else {
                    Log.d(Constant.TAG, "ManageTeamViewModel Field EE : ${response.message()}")
                }
            }.onFailure {
                Log.d(Constant.TAG, "ManageTeamViewModel kill them all : $result")
            }
            _isLoading.value = false // 로딩 종료
        }
    }

    // TODO: 화면에 즉각 적용하기
    fun createOrganization(name: String, onSuccess: () -> Unit, onFailed: () -> Unit) {
        Log.d(Constant.TAG, "조직 만들기 name: $name")
        viewModelScope.launch {
            createOrganizationUserCase(
                param = CreateOrganizationUserCase.Param(organizationName = name)
            ).onSuccess {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    Log.d(Constant.TAG, "조직 만들기 실패 ${it.message()}")
                    onFailed()
                }
            }.onFailure {
                onFailed()
            }
        }
    }

    // TODO: 조직 화면으로 넘기기
    fun joinOrganization(code: String, onSuccess: () -> Unit, onFailed: () -> Unit) {
        Log.d(Constant.TAG, "조직 참여하기 code: $code")
        viewModelScope.launch {
            joinOrganizationUserCase(
                param = JoinOrganizationUserCase.Param(organizationCode = code)
            ).onSuccess {
                if (it.isSuccessful) {
                    Log.d(Constant.TAG, "조직 참여하기 성공! ${it.body()}")
                    onSuccess()
                } else {
                    Log.d(Constant.TAG, "조직 참여하기 실패 ${it.message()} ${it.code()}")
                    onFailed()
                }
            }.onFailure {
                Log.d(Constant.TAG, "조직 참여하기 실패 ${it.message} ${it.cause}")
                onFailed()
            }
        }
    }

    fun getOrganization(organizationId: Int, onSuccess: (Int) -> Unit, onFailed: () -> Unit) {
        Log.d(Constant.TAG, "조직 조회 organizationId: $organizationId")
        viewModelScope.launch {
            getOrganizationUseCase(
                param = GetOrganizationUseCase.Param(organizationId = organizationId)
            ).onSuccess {
                if (it.isSuccessful) {
                    Log.d(Constant.TAG, "조직 조회 성공! ${it.body()}")
                    onSuccess(it.body()!!.id)
                } else {
                    Log.d(Constant.TAG, "조직 조회 실패 ${it.message()}")
                    onFailed()
                }
            }.onFailure {
                Log.d(Constant.TAG, "조직 조회 실패 ${it.message} ${it.cause}")
                onFailed()
            }
        }
    }
}
