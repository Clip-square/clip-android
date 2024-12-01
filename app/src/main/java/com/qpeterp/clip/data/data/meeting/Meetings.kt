package com.qpeterp.clip.data.data.meeting

import com.qpeterp.clip.data.data.auth.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMeeting(
    val message: String,
    val meeting: MeetingData
)

@Serializable
data class Meetings(
    val meetings: List<MeetingData>
)

@Serializable
data class MeetingData(
    val id: Int,
    val title: String,
    @SerialName("total_duration")
    val totalDuration: String,
    val organization: Int,
    @SerialName("save_minutes")
    val saveMinutes: Boolean,
    val sections: List<Section>,
    val participants: List<MeetingUser>,
    @SerialName("is_active")
    val isActive: ActiveType,
    val creator: Int,
    @SerialName("start_time")
    val startTime: String?,
)

@Serializable
data class MeetingUser(
    val user: User
)

@Serializable
data class Section(
    val name: String,
    @SerialName("end_time")
    val endTime: String?
)