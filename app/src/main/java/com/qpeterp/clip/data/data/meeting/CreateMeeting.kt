package com.qpeterp.clip.data.data.meeting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMeeting(
    val title: String,
    @SerialName("total_duration")
    val totalDuration: String,
    val organization: Int,
    val sections: List<Section>,
    @SerialName("user_ids")
    val userIds: List<Int>,
    @SerialName("save_minutes")
    val saveMinutes: Boolean,
    val creator: Int,
    @SerialName("start_time")
    val startTime: String
)