package com.qpeterp.clip.data.repository

import com.qpeterp.clip.data.data.meeting.Meeting
import com.qpeterp.clip.data.remote.service.MeetingService
import com.qpeterp.clip.domain.repository.MeetingRepository
import retrofit2.Response
import javax.inject.Inject

class MeetingRepositoryImpl @Inject constructor(
    private val meetingService: MeetingService,
) : MeetingRepository {
    override suspend fun getMeetingList(): Response<Meeting> {
        val result = meetingService.getMeetingList()

        return result
    }
}