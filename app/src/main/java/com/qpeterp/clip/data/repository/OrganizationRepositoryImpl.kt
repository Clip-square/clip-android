package com.qpeterp.clip.data.repository

import com.qpeterp.clip.data.data.organization.Organizations
import com.qpeterp.clip.data.remote.service.OrganizationService
import com.qpeterp.clip.domain.repository.OrganizationRepository
import retrofit2.Response
import javax.inject.Inject

class OrganizationRepositoryImpl @Inject constructor(
    private val organizationService: OrganizationService
) : OrganizationRepository {
    override suspend fun getOrganizations(): Response<List<Organizations>> {
        val result = organizationService.getOrganizations()

        return result
    }
}