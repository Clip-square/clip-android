package com.qpeterp.clip.data.remote.service

import com.qpeterp.clip.data.data.meeting.CreateMeeting
import com.qpeterp.clip.data.data.meeting.MeetingData
import com.qpeterp.clip.data.data.meeting.Meetings
import com.qpeterp.clip.data.data.meeting.ResponseMeeting
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MeetingService {
    @GET("meetings/")
    suspend fun getMeetingList(): Response<Meetings>

    @GET("meetings/{meeting_id}/")
    suspend fun getMeeting(
        @Path("meeting_id") meetingId: String
    ): Response<MeetingData>

    @POST("meetings/")
    suspend fun createMeeting(
        @Body data: CreateMeeting
    ): Response<ResponseMeeting>

    @POST("meetings/status/{meeting_id}/")
    suspend fun startMeeting(
        @Path("meeting_id") meetingId: String
    ): Response<Unit>

    @HTTP(method = "DELETE", path = "meetings/status/{meeting_id}/", hasBody = true)
    @Multipart
//    @DELETE("meetings/status/{meeting_id}/")
    suspend fun endMeeting(
        @Part fileParts: MultipartBody.Part,  // 파일 리스트
        @Path("meeting_id") meetingId: String,
        @Part("total_duration") totalDuration: RequestBody,
        @Part("start_time") startTime: RequestBody,
        @Part("section_end_times[]") vararg sectionEndTimes: RequestBody // vararg로 동적으로 개별 파트 전달
    ): Response<Unit>
}