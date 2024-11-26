package com.qpeterp.clip.data.data.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Organizations(
    val id: Int,
    val name: String,
    @SerialName("invite_code")
    val inviteCode: String,
    @SerialName("created_at")
    val createdAt: String,
    val members: List<Int>
)
