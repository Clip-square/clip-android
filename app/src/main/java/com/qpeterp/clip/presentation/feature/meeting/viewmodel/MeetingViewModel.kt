package com.qpeterp.clip.presentation.feature.meeting.viewmodel

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.clip.application.ClipApplication
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.data.data.meeting.CreateMeeting
import com.qpeterp.clip.data.data.meeting.MeetingData
import com.qpeterp.clip.data.data.meeting.Section
import com.qpeterp.clip.data.data.organization.OrganizationMember
import com.qpeterp.clip.domain.usecase.meeting.AudioRecodeUseCase
import com.qpeterp.clip.domain.usecase.meeting.CreateMeetingUseCase
import com.qpeterp.clip.domain.usecase.meeting.EndMeetingUseCase
import com.qpeterp.clip.domain.usecase.meeting.StartMeetingUseCase
import com.qpeterp.clip.domain.usecase.organization.GetOrganizationMembersUseCase
import com.qpeterp.clip.presentation.common.Common
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class MeetingViewModel @Inject constructor(
    private val organizationMembersUseCase: GetOrganizationMembersUseCase,
    private val createMeetingUseCase: CreateMeetingUseCase,
    private val startMeetingUseCase: StartMeetingUseCase,
    private val endMeetingUseCase: EndMeetingUseCase,
) : ViewModel() {
    // SetupViewModel
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================

    private val _meetingSubTopicList = MutableStateFlow(listOf(Section("", null)))
    val meetingSubTopicList: StateFlow<List<Section>> get() = _meetingSubTopicList

    // 로딩 상태 추가
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _memberList = MutableStateFlow<List<OrganizationMember>>(emptyList())
    val memberList: StateFlow<List<OrganizationMember>> get() = _memberList

    private val _partUserId = MutableStateFlow<List<Int>>(emptyList())
    val partUserId = _partUserId.asStateFlow()

    init {
        getOrganizationMembers(Common.organizationId)
    }

    fun toggleUserId(userId: Int) {
        _partUserId.value = if (userId in _partUserId.value) {
            _partUserId.value - userId
        } else {
            _partUserId.value + userId
        }
        Log.d(Constant.TAG, "현재 참가 유저 id : ${_partUserId.value}")
    }

    fun addSubTopic() {
        _meetingSubTopicList.value += Section("", null)
    }

    fun updateSubTopicText(subTopicIndex: Int, newContent: String) {
        _meetingSubTopicList.value = _meetingSubTopicList.value.mapIndexed { index, subTopic ->
            (if (index == subTopicIndex) Section(newContent, null) else subTopic)
        }
    }

    fun removeSubTopic(subTopicIndex: Int) {
        _meetingSubTopicList.value =
            _meetingSubTopicList.value.filterIndexed { index, _ -> index != subTopicIndex } // 지정된 인덱스 삭제
    }

    private fun getOrganizationMembers(organizationId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            organizationMembersUseCase(
                param = GetOrganizationMembersUseCase.Param(organizationId)
            ).onSuccess {
                if (it.isSuccessful) {
                    _memberList.value = it.body()!!.members
                    Log.d(
                        Constant.TAG,
                        "SetupViewModel getOrganizationMembers data : ${it.body()!!.members}"
                    )
                } else {
                    Log.e(
                        Constant.TAG,
                        "SetupViewModel getOrganizationMembers Error : ${it.message()} ${it.code()}"
                    )
                }
                _isLoading.value = false
            }.onFailure {
                Log.e(Constant.TAG, "SetupViewModel getOrganizationMembers EEE : ${it.message}")
                _isLoading.value = false
            }
        }
    }

    fun createMeeting(
        meetingTopic: String,
        meetingTime: String,
        onSuccess: (MeetingData) -> Unit,
        onFailed: () -> Unit,
    ) {
        val request = CreateMeetingUseCase.Param(
            data = CreateMeeting(
                title = meetingTopic,
                totalDuration = meetingTime,
                organization = Common.organizationId,
                sections = _meetingSubTopicList.value,
                userIds = _partUserId.value,
                saveMinutes = true,
                creator = ClipApplication.prefs.userId,
                startTime = formatDateTime()
            )
        )

        val jsonData = kotlinx.serialization.json.Json.encodeToString(request)
        Log.d(Constant.TAG, "Serialized JSON: $jsonData")

        viewModelScope.launch {
            createMeetingUseCase(
                param = request
            ).onSuccess {
                if (it.isSuccessful) {
                    startMeeting(
                        it.body()!!.meeting.id.toString(),
                        onSuccess = {
                            Log.d(
                                Constant.TAG,
                                "SetupViewModel createMeeting Success : ${it.body()}"
                            )
                            onSuccess(it.body()!!.meeting)
                        },
                        onFailed = {
                            Log.e(
                                Constant.TAG,
                                "SetupViewModel startMeeting Failed : ${it.message()} ${it.code()}"
                            )
                            onFailed()
                        }
                    )
                } else {
                    Log.e(
                        Constant.TAG,
                        "SetupViewModel createMeeting Failed : ${it.errorBody()} ${it.code()}"
                    )
                    onFailed()
                }
            }.onFailure {
                Log.e(Constant.TAG, "SetupViewModel createMeeting EEE : ${it.message}")
                onFailed()
            }
        }
    }

    private fun startMeeting(
        meetingId: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        Log.d(Constant.TAG, "meetingId : $meetingId")
        viewModelScope.launch {
            startMeetingUseCase(
                param = StartMeetingUseCase.Param(data = meetingId)
            ).onSuccess {
                if (it.isSuccessful) {
                    Log.d(Constant.TAG, "SetupViewModel startMeeting Success : ${it.body()}")
                    onSuccess()
                } else {
                    Log.d(
                        Constant.TAG,
                        "SetupViewModel startMeeting Failed : ${it.message()} ${it.code()}"
                    )
                    onFailed()
                }
            }.onFailure {
                onFailed()
            }
        }
    }

    private fun formatDateTime(): String {
        val now = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC") // UTC 타임존 설정
        return formatter.format(now)
    }

    // MeetingViewModel
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    private val job = Job()  // 코루틴의 Job을 수동으로 생성

    private val recodeUseCase = AudioRecodeUseCase()

    private val _meetingState = MutableStateFlow(true)
    val meetingState: StateFlow<Boolean> get() = _meetingState

    private val _recordFile = MutableStateFlow<File?>(null) // 초기값은 null

    private val _sectionsEndTime = MutableStateFlow<List<String>>(emptyList())

    private val _subTopicIndex = MutableStateFlow(0)
    val subTopicIndex: StateFlow<Int> get() = _subTopicIndex

    fun addSectionsEndTime(time: String) {
        _sectionsEndTime.value += time
    }

    fun changeMeetingState() {
        _meetingState.value = !_meetingState.value
    }

    fun updateSubTopicIndex(index: Int) {
        _subTopicIndex.value = index
    }

    fun startRecode(
        context: Context,
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        val job = viewModelScope.launch(this.job + Dispatchers.IO) { // 녹음 작업은 IO 스레드에서 처리
            try {
                val recordedData = recodeUseCase.startRecording(context)

                _recordFile.value = recordedData
                Log.d(Constant.TAG, "recordFile 저장 완료")

                // UI 관련 작업을 메인 스레드에서 실행
                withContext(Dispatchers.Main) {
                    endMeeting(
                        onSuccess = { onSuccess() },
                        onFailed = { onFailed() }
                    )
                }
            } catch (e: CancellationException) {
                Log.e(Constant.TAG, "코루틴이 취소되었습니다: ${e.message}")
            } catch (e: Exception) {
                // 예외가 발생해도 UI 관련 동작은 Main에서 호출
                withContext(Dispatchers.Main) {
                    onFailed()
                }
                Log.e(Constant.TAG, "녹음 중 오류 발생: ${e.message}")
            }
        }
    }

    fun pauseRecode() {
        recodeUseCase.pauseRecording()
    }

    fun resumeRecode() {
        recodeUseCase.resumeRecording()
    }

    fun stopRecode() {
        Log.d(Constant.TAG, "MeetingViewModel stopRecode is running")
        recodeUseCase.stopRecording()
    }

    private fun endMeeting(
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw IllegalStateException("endMeeting 은 메인 스레드에서 불러야 함.")
        }

        Log.d(Constant.TAG, "MeetingViewModel endMeeting is running")
        viewModelScope.launch(job + Dispatchers.IO) {  // 백그라운드 스레드 작업
            try {
                endMeetingUseCase(
                    meetingId = _meetingData.value!!.id.toString(),
                    totalDuration = _totalTime,
                    startTime = _startTime.value!!,
                    sectionEndTimes = _sectionsEndTime.value,
                    recordFile = _recordFile.value!!
                ).onSuccess {
                    if (it.isSuccessful) {
                        Log.d(Constant.TAG, "회의 종료 성공")
                        withContext(Dispatchers.Main) {
                            onSuccess() // 메인 스레드에서 실행
                        }
                    } else {
                        Log.d(Constant.TAG, "회의 종료 실패: ${it.code()} ${it.message()}")
                        withContext(Dispatchers.Main) {
                            onFailed() // 메인 스레드에서 실행
                        }
                    }
                }.onFailure {
                    Log.d(Constant.TAG, "회의 종료 실패: ${it.message}")
                    withContext(Dispatchers.Main) {
                        onFailed() // 메인 스레드에서 실행
                    }
                }
            } catch (e: Exception) {
                Log.e(Constant.TAG, "endMeeting 중 오류 발생: ${e.message}")
                withContext(Dispatchers.Main) {
                    onFailed() // 메인 스레드에서 실행
                }
            }
        }
    }

    private var _totalTime: String = ""

    fun setTotalDuration(time: String) {
        Log.d(Constant.TAG, "경과된 시간 : $time")
        _totalTime = time
    }

    // SharedViewModel
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================
    //=============================//=============================//=============================

    private val _meetingData = MutableStateFlow<MeetingData?>(null)
    val meetingData: StateFlow<MeetingData?> = _meetingData

    private val _startTime = MutableStateFlow<String?>(null)

    fun setMeetingData(meetingData: MeetingData) {
        _startTime.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        _meetingData.value = meetingData
    }
}