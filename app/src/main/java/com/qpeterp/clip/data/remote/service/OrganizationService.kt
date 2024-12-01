package com.qpeterp.clip.data.remote.service

import com.qpeterp.clip.data.data.auth.User
import com.qpeterp.clip.data.data.organization.Members
import com.qpeterp.clip.data.data.organization.Organization
import com.qpeterp.clip.data.data.organization.OrganizationCode
import com.qpeterp.clip.data.data.organization.OrganizationName
import com.qpeterp.clip.data.data.organization.Organizations
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrganizationService {
    @GET("organizations/")
    suspend fun getOrganizationList(): Response<List<Organizations>>

    @POST("organizations/")
    suspend fun createOrganizations(
        @Body data: OrganizationName
    ): Response<Organizations>

    @POST("organizations/invite/")
    suspend fun joinOrganizations(
        @Body data: OrganizationCode
    ): Response<Unit>

    @GET("organizations/{organization_id}/")
    suspend fun getOrganization(
        @Path("organization_id") organizationId: String
    ): Response<Organization>

    @GET("organizations/members/{organization_id}/")
    suspend fun getOrganizationMembers(
        @Path("organization_id") organizationId: String
    ): Response<Members>
}