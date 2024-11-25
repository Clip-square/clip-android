package com.qpeterp.clip.data.data.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val user: User,
    val message: String,
    val token: Token
)

@Serializable
data class User(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("is_superuser")
    val isSuperuser: Boolean,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("is_staff")
    val isStaff: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
)

@Serializable
data class Token(
    val access: String,
    val refresh: String,
)
