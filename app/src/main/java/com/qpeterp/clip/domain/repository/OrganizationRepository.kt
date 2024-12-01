package com.qpeterp.clip.domain.repository

import com.qpeterp.clip.data.data.organization.Members
import com.qpeterp.clip.data.data.organization.Organization
import com.qpeterp.clip.data.data.organization.Organizations
import retrofit2.Response

interface OrganizationRepository {
    suspend fun getOrganizationList(): Response<List<Organizations>>

    suspend fun createOrganizations(organizationName: String): Response<Organizations>

    suspend fun joinOrganizations(organizationCode: String): Response<Unit>

    suspend fun getOrganization(organizationId: Int): Response<Organization>

    suspend fun getOrganizationMembers(organizationId: Int): Response<Members>
}