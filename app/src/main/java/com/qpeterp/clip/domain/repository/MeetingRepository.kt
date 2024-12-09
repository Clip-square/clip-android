package com.qpeterp.clip.domain.repository

import com.qpeterp.clip.data.data.meeting.CreateMeeting
import com.qpeterp.clip.data.data.meeting.MeetingData
import com.qpeterp.clip.data.data.meeting.Meetings
import com.qpeterp.clip.data.data.meeting.ResponseMeeting
import retrofit2.Response
import java.io.File

interface MeetingRepository {
    suspend fun getMeetingList(): Response<Meetings>

    suspend fun getMeeting(meetingId: String): Response<MeetingData>

    suspend fun createMeeting(meetingData: CreateMeeting): Response<ResponseMeeting>

    suspend fun startMeeting(meetingId: String): Response<Unit>

    suspend fun endMeeting(
        meetingId: String,
        recordList: File,  // 여러 파일을 처리하는 리스트
        totalDuration: String,
        sectionEndTimes: List<String>,
        startTime: String
    ): Response<Unit>
}