package com.qpeterp.clip.domain.usecase.meeting

import com.qpeterp.clip.data.data.meeting.MeetingData
import com.qpeterp.clip.domain.repository.MeetingRepository
import retrofit2.Response
import javax.inject.Inject

class GetMeetingUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(meetingId: String): Result<Response<MeetingData>> {
        return runCatching {
            meetingRepository.getMeeting(meetingId)
        }
    }
}