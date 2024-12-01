package com.qpeterp.clip.data.data.organization

import kotlinx.serialization.Serializable

@Serializable
data class Members(
    val members: List<OrganizationMember>
)

@Serializable
data class OrganizationMember(
    val id: Int,
    val name: String,
    val email: String,
    val organization: String,
)
