package com.qpeterp.clip.domain.repository

import com.qpeterp.clip.data.data.meeting.CreateMeeting
import com.qpeterp.clip.data.data.meeting.Meetings
import com.qpeterp.clip.data.data.meeting.ResponseMeeting
import retrofit2.Response

interface MeetingRepository {
    suspend fun getMeetingList(): Response<Meetings>

    suspend fun createMeeting(meetingData: CreateMeeting): Response<ResponseMeeting>

    suspend fun startMeeting(meetingId: String): Response<Unit>
}