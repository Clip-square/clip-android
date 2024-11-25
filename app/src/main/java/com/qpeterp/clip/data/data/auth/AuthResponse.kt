package com.qpeterp.clip.data.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val user: User,
    val message: String,
    val token: Token
)

@Serializable
data class User(
    val id: Int,
    val email: String,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class Token(
    val access: String,
    val refresh: String,
)
