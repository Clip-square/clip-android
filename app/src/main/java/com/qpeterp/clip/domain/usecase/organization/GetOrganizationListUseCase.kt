package com.qpeterp.clip.domain.usecase.organization

import com.qpeterp.clip.data.data.organization.Organizations
import com.qpeterp.clip.domain.repository.OrganizationRepository
import retrofit2.Response
import javax.inject.Inject

class GetOrganizationListUseCase @Inject constructor(
    private val organizationRepository: OrganizationRepository
) {
    suspend operator fun invoke(): Result<Response<List<Organizations>>> {
        return runCatching {
            organizationRepository.getOrganizationList()
        }
    }
}