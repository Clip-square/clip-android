package com.qpeterp.clip.domain.repository

import com.qpeterp.clip.data.data.meeting.Meeting
import retrofit2.Response

interface MeetingRepository {
    suspend fun getMeetingList(): Response<Meeting>
}