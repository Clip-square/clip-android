package com.qpeterp.clip.data.remote.service

import com.qpeterp.clip.data.data.meeting.Meeting
import com.qpeterp.clip.data.data.organization.Organization
import com.qpeterp.clip.data.data.organization.OrganizationCode
import com.qpeterp.clip.data.data.organization.OrganizationName
import com.qpeterp.clip.data.data.organization.Organizations
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetingService {
    @GET("meetings/")
    suspend fun getMeetingList(): Response<Meeting>

    @GET("meetings/{meeting_id}/")
    suspend fun getMeeting(
        @Path("meeting_id") meetingId: String
    ): Response<Organization>
}