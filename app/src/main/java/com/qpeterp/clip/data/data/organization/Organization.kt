package com.qpeterp.clip.data.data.organization

import com.qpeterp.clip.data.data.auth.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Organization(
    val id: Int,
    val name: String,
    val owner: String,
    @SerialName("invite_code")
    val inviteCode: String,
    @SerialName("created_at")
    val createdAt: String,
    val members: List<Member>,
)

@Serializable
data class Member(
    val id: Int,
    val organization: Int,
    val user: User,
    @SerialName("joined_at")
    val joinedAt: String,
)