package com.qpeterp.clip.di.module

import com.qpeterp.clip.data.repository.AuthRepositoryImpl
import com.qpeterp.clip.domain.repository.AuthRepository
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
}