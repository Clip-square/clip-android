package com.qpeterp.clip.domain.usecase.meeting

import android.util.Log
import com.qpeterp.clip.common.Constant
import com.qpeterp.clip.domain.repository.MeetingRepository
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class EndMeetingUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    suspend operator fun invoke(
        meetingId: String,
        recordFile: File,
        totalDuration: String,
        sectionEndTimes: List<String>,
        startTime: String
    ): Result<Response<Unit>> {
        Log.d(Constant.TAG, "MeetingRepositoryImpl endMeeting is running11111111111111111111111111")

        return runCatching {
            meetingRepository.endMeeting(
                meetingId = meetingId,
                recordList = recordFile,
                totalDuration = totalDuration,
                sectionEndTimes = sectionEndTimes,
                startTime = startTime
            )
        }
    }


}
