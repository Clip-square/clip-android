package com.qpeterp.clip.data.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val email: String,
    val password: String,
)
