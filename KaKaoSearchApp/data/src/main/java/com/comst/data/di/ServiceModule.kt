package com.comst.data.di

import com.comst.data.api.KaKaoSearchService
import com.comst.data.api.SampleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideSampleService(
        retrofit: Retrofit
    ): SampleService {
        return retrofit.create(SampleService::class.java)
    }

    @Singleton
    @Provides
    fun provideKaKaoSearchService(
        retrofit: Retrofit
    ): KaKaoSearchService {
        return retrofit.create(KaKaoSearchService::class.java)
    }

}