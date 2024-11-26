package com.qpeterp.clip.domain.repository

import com.qpeterp.clip.data.data.auth.AuthResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Response<AuthResponse>

    suspend fun register(
        email: String,
        password: String,
        name: String
    ): Response<AuthResponse>

    suspend fun logout(
    ): Response<Unit>
}