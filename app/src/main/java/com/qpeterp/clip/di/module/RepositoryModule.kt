package com.qpeterp.clip.di.module

import com.qpeterp.clip.data.repository.AuthRepositoryImpl
import com.qpeterp.clip.data.repository.MeetingRepositoryImpl
import com.qpeterp.clip.data.repository.OrganizationRepositoryImpl
import com.qpeterp.clip.domain.repository.AuthRepository
import com.qpeterp.clip.domain.repository.MeetingRepository
import com.qpeterp.clip.domain.repository.OrganizationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun providesAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun providesMeetingRepository(
        meetingRepositoryImpl: MeetingRepositoryImpl
    ): MeetingRepository

    @Singleton
    @Binds
    abstract fun providesOrganizationRepository(
        organizationRepositoryImpl: OrganizationRepositoryImpl
    ): OrganizationRepository
}