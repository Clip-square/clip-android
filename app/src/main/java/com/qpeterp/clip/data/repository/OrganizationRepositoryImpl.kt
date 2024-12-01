package com.qpeterp.clip.data.repository

import com.qpeterp.clip.data.data.organization.Members
import com.qpeterp.clip.data.data.organization.Organization
import com.qpeterp.clip.data.data.organization.OrganizationCode
import com.qpeterp.clip.data.data.organization.OrganizationName
import com.qpeterp.clip.data.data.organization.Organizations
import com.qpeterp.clip.data.remote.service.OrganizationService
import com.qpeterp.clip.domain.repository.OrganizationRepository
import retrofit2.Response
import javax.inject.Inject

class OrganizationRepositoryImpl @Inject constructor(
    private val organizationService: OrganizationService,
) : OrganizationRepository {
    override suspend fun getOrganizationList(): Response<List<Organizations>> {
        val result = organizationService.getOrganizationList()

        return result
    }

    override suspend fun createOrganizations(organizationName: String): Response<Organizations> {
        val result = organizationService.createOrganizations(OrganizationName(organizationName))

        return result
    }

    override suspend fun joinOrganizations(organizationCode: String): Response<Unit> {
        return organizationService.joinOrganizations(OrganizationCode(organizationCode))
    }

    override suspend fun getOrganization(organizationId: Int): Response<Organization> {
        return organizationService.getOrganization(organizationId.toString())
    }

    override suspend fun getOrganizationMembers(organizationId: Int): Response<Members> {
        return organizationService.getOrganizationMembers(organizationId.toString())
    }
}