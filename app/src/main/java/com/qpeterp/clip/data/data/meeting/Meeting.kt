package com.qpeterp.clip.data.data.meeting

import com.qpeterp.clip.data.data.auth.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meeting(
    val meetings: List<MeetingData>
)

@Serializable
data class MeetingData(
    val id: Int,
    val title: String,
    @SerialName("total_duration")
    val totalDuration: Int,
    val organization: String,
    @SerialName("save_minutes")
    val saveMinutes: Boolean,
    val sections: List<Section>,
    val participants: List<MeetingUser>,
    @SerialName("created_at")
    val createdAt: String,
)

@Serializable
data class MeetingUser(
    val user: User
)

@Serializable
data class Section(
    val name: String
)