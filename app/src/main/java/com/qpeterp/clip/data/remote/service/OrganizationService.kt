package com.qpeterp.clip.data.remote.service

import com.qpeterp.clip.data.data.organization.Organizations
import retrofit2.Response
import retrofit2.http.GET

interface OrganizationService {
    @GET("organizations/")
    suspend fun getOrganizations(): Response<List<Organizations>>
}