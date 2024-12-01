package com.qpeterp.clip.data.remote.service

import com.qpeterp.clip.data.data.meeting.CreateMeeting
import com.qpeterp.clip.data.data.meeting.Meetings
import com.qpeterp.clip.data.data.meeting.ResponseMeeting
import com.qpeterp.clip.data.data.organization.Organization
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetingService {
    @GET("meetings/")
    suspend fun getMeetingList(): Response<Meetings>

    @GET("meetings/{meeting_id}/")
    suspend fun getMeeting(
        @Path("meeting_id") meetingId: String
    ): Response<Organization>

    @POST("meetings/")
    suspend fun createMeeting(
        @Body data: CreateMeeting
    ): Response<ResponseMeeting>

    @POST("meetings/status/{meeting_id}/")
    suspend fun startMeeting(
        @Path("meeting_id") meetingId: String
    ): Response<Unit>
}