package com.qpeterp.clip.domain.usecase.auth

import com.qpeterp.clip.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Response<Unit>> {
        return runCatching {
            authRepository.logout()
        }
    }
}