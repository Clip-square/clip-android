package com.qpeterp.clip.data.repository

import com.qpeterp.clip.data.data.meeting.CreateMeeting
import com.qpeterp.clip.data.data.meeting.Meetings
import com.qpeterp.clip.data.data.meeting.ResponseMeeting
import com.qpeterp.clip.data.remote.service.MeetingService
import com.qpeterp.clip.domain.repository.MeetingRepository
import retrofit2.Response
import javax.inject.Inject

class MeetingRepositoryImpl @Inject constructor(
    private val meetingService: MeetingService,
) : MeetingRepository {
    override suspend fun getMeetingList(): Response<Meetings> {
        return meetingService.getMeetingList()
    }

    override suspend fun createMeeting(meetingData: CreateMeeting): Response<ResponseMeeting> {
        return meetingService.createMeeting(meetingData)
    }

    override suspend fun startMeeting(meetingId: String): Response<Unit> {
        return meetingService.startMeeting(meetingId)
    }
}