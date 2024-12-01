package com.qpeterp.clip.domain.usecase.organization

import com.qpeterp.clip.data.data.organization.Members
import com.qpeterp.clip.domain.repository.OrganizationRepository
import retrofit2.Response
import javax.inject.Inject

class GetOrganizationMembersUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(param: Param): Result<Response<Members>> {
        return runCatching {
            organizationRepository.getOrganizationMembers(param.organizationId)
        }
    }

    data class Param(
        val organizationId: Int
    )
}