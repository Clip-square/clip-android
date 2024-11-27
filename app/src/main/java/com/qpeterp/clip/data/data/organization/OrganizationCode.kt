package com.qpeterp.clip.data.data.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrganizationCode(
    @SerialName("invite_code")
    val inviteCode: String,
)