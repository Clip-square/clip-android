package com.qpeterp.clip.data.repository

import com.qpeterp.clip.data.data.auth.AuthResponse
import com.qpeterp.clip.data.data.auth.Login
import com.qpeterp.clip.data.data.auth.Register
import com.qpeterp.clip.data.remote.service.AuthService
import com.qpeterp.clip.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Response<AuthResponse> {
        return authService.login(
            Login(
                email = email,
                password = password
            )
        )
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String
    ): Response<AuthResponse> {
        return authService.register(
            Register(
                email = email,
                password = password,
                name = name
            )
        )
    }
}