package com.qpeterp.clip.data.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class Register(
    val email: String,
    val name: String,
    val password: String
)