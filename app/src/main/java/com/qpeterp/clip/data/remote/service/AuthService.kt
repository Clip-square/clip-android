package com.qpeterp.clip.data.remote.service

import com.qpeterp.clip.data.data.auth.AuthResponse
import com.qpeterp.clip.data.data.auth.Login
import com.qpeterp.clip.data.data.auth.Register
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface AuthService {
    @POST("accounts/auth/")
    suspend fun login(
        @Body loginData: Login
    ): Response<AuthResponse>

    @DELETE("accounts/auth/")
    suspend fun logout(): Response<Unit>

    @POST("accounts/register/")
    suspend fun register(
        @Body registerData: Register
    ): Response<AuthResponse>
}