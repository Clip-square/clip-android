package com.qpeterp.clip.domain.repository

import com.qpeterp.clip.data.data.organization.Organizations
import retrofit2.Response

interface OrganizationRepository {
    suspend fun getOrganizations(): Response<List<Organizations>>
}