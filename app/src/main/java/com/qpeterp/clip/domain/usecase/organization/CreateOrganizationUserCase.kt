package com.qpeterp.clip.domain.usecase.organization

import com.qpeterp.clip.data.data.organization.Organizations
import com.qpeterp.clip.domain.repository.OrganizationRepository
import retrofit2.Response
import javax.inject.Inject

class CreateOrganizationUserCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(param: Param): Result<Response<Organizations>> {
        return runCatching {
            organizationRepository.createOrganizations(param.organizationName)
        }
    }

    data class Param(
        val organizationName: String
    )
}