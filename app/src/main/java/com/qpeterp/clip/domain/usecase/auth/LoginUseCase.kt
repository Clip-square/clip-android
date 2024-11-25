package com.qpeterp.clip.domain.usecase.auth

import com.qpeterp.clip.data.data.auth.AuthResponse
import com.qpeterp.clip.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(param: Param): Result<Response<AuthResponse>> {
        return runCatching {
            authRepository.login(param.email, param.password)
        }
    }

    data class Param(
        val email: String,
        val password: String
    )
}