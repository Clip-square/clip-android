package com.qpeterp.clip.domain.usecase.meeting

import com.qpeterp.clip.domain.repository.MeetingRepository
import kotlinx.serialization.Serializable
import retrofit2.Response
import javax.inject.Inject

class StartMeetingUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(param: Param): Result<Response<Unit>> {
        return runCatching {
            meetingRepository.startMeeting(param.data)
        }
    }

    @Serializable
    data class Param(
        val data: String
    )
}