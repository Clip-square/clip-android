package com.qpeterp.clip.domain.usecase.meeting

import com.qpeterp.clip.data.data.meeting.CreateMeeting
import com.qpeterp.clip.data.data.meeting.ResponseMeeting
import com.qpeterp.clip.domain.repository.MeetingRepository
import kotlinx.serialization.Serializable
import retrofit2.Response
import javax.inject.Inject

class CreateMeetingUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(param: Param): Result<Response<ResponseMeeting>> {
        return runCatching {
            meetingRepository.createMeeting(param.data)
        }
    }

    @Serializable
    data class Param(
        val data: CreateMeeting
    )
}