package com.qpeterp.clip.di.module

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.qpeterp.clip.BuildConfig
import com.qpeterp.clip.data.remote.service.AuthService
import com.qpeterp.clip.data.remote.service.MeetingService
import com.qpeterp.clip.data.remote.service.OrganizationService
import com.qpeterp.clip.domain.usecase.meeting.CreateMeetingUseCase
import com.qpeterp.clip.domain.usecase.meeting.StartMeetingUseCase
import com.qpeterp.clip.domain.usecase.organization.GetOrganizationMembersUseCase
import com.qpeterp.clip.presentation.feature.meeting.viewmodel.MeetingViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.JavaNetCookieJar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.net.CookieManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private fun fetchApiBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(fetchApiBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideMeetingService(retrofit: Retrofit): MeetingService =
        retrofit.create(MeetingService::class.java)

    @Provides
    @Singleton
    fun provideOrganizationService(retrofit: Retrofit): OrganizationService =
        retrofit.create(OrganizationService::class.java)

    @Provides
    @Singleton
    fun provideMeetingViewModel(
        organizationMembersUseCase: GetOrganizationMembersUseCase,
        createMeetingUseCase: CreateMeetingUseCase,
        startMeetingUseCase: StartMeetingUseCase
    ): MeetingViewModel {
        return MeetingViewModel(
            organizationMembersUseCase,
            createMeetingUseCase,
            startMeetingUseCase
        )
    }
}
