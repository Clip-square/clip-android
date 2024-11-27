package com.qpeterp.clip.domain.usecase.organization

import com.qpeterp.clip.data.data.organization.Organization
import com.qpeterp.clip.domain.repository.OrganizationRepository
import retrofit2.Response
import javax.inject.Inject

class GetOrganizationUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(param: Param): Result<Response<Organization>> {
        return runCatching {
            organizationRepository.getOrganization(param.organizationId)
        }
    }

    data class Param(
        val organizationId: Int
    )
}