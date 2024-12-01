package com.qpeterp.clip.domain.usecase.meeting

import com.qpeterp.clip.data.data.meeting.Meetings
import com.qpeterp.clip.domain.repository.MeetingRepository
import retrofit2.Response
import javax.inject.Inject

class GetMeetingListUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(): Result<Response<Meetings>> {
        return runCatching {
            meetingRepository.getMeetingList()
        }
    }
}