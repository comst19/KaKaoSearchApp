package com.comst.data.di

import com.comst.data.repository.token.TokenRepositoryImpl
import com.comst.data.repository.sample.SampleRepositoryImpl
import com.comst.domain.repository.SampleRepository
import com.comst.domain.repository.TokenRepository
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
    abstract fun bindTokenRepository(
        tokenRepository: TokenRepositoryImpl
    ): TokenRepository

    @Singleton
    @Binds
    abstract fun bindSampleRepository(
        sampleRepository: SampleRepositoryImpl
    ): SampleRepository
}