package com.qpeterp.clip.data.repository

import android.util.Log
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.data.data.meeting.CreateMeeting
import com.qpeterp.clip.data.data.meeting.MeetingData
import com.qpeterp.clip.data.data.meeting.Meetings
import com.qpeterp.clip.data.data.meeting.ResponseMeeting
import com.qpeterp.clip.data.remote.service.MeetingService
import com.qpeterp.clip.domain.repository.MeetingRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class MeetingRepositoryImpl @Inject constructor(
    private val meetingService: MeetingService,
) : MeetingRepository {
    override suspend fun getMeetingList(): Response<Meetings> {
        return meetingService.getMeetingList()
    }

    override suspend fun getMeeting(meetingId: String): Response<MeetingData> {
        return meetingService.getMeeting(meetingId)
    }

    override suspend fun createMeeting(meetingData: CreateMeeting): Response<ResponseMeeting> {
        return meetingService.createMeeting(meetingData)
    }

    override suspend fun startMeeting(meetingId: String): Response<Unit> {
        return meetingService.startMeeting(meetingId)
    }

    override suspend fun endMeeting(
        meetingId: String,
        recordList: File,
        totalDuration: String,
        sectionEndTimes: List<String>,
        startTime: String,
    ): Response<Unit> {
        Log.d(Constant.TAG, "MeetingRepositoryImpl endMeeting is running22222222222222222222")

        Log.d(Constant.TAG, "sectionEndTimes is $sectionEndTimes")

        val sectionEndTimesParts = sectionEndTimes.map { endTime ->
            // "text/plain" 타입으로 RequestBody 생성 (서버에서 기대하는 타입에 맞게 수정 가능)
            RequestBody.create("text/plain".toMediaTypeOrNull(), endTime)
        }

        return meetingService.endMeeting(
            meetingId = meetingId,
            fileParts =  createMultipartBody(recordList, "record_file"),  // fileParts는 List<MultipartBody.Part> 형식이어야 하므로 리스트로 감쌈
            totalDuration = createRequestBody(totalDuration),
            sectionEndTimes = sectionEndTimesParts.toTypedArray(),
            startTime = createRequestBody(startTime)
        )
    }

    private fun createMultipartBody(file: File, paramName: String): MultipartBody.Part {
        val requestBody = file.asRequestBody("audio/wav".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(paramName, file.name, requestBody)
    }

    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}