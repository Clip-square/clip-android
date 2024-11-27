package com.qpeterp.clip.domain.usecase.organization

import com.qpeterp.clip.domain.repository.OrganizationRepository
import retrofit2.Response
import javax.inject.Inject

class JoinOrganizationUserCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(param: Param): Result<Response<Unit>> {
        return runCatching {
            organizationRepository.joinOrganizations(param.organizationCode)
        }
    }

    data class Param(
        val organizationCode: String
    )
}