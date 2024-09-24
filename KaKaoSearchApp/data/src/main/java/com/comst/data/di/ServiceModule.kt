package com.comst.data.di

import com.comst.data.api.SampleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideSampleService(
        @NetworkModule.PublicRetrofit retrofit: Retrofit
    ): SampleService {
        return retrofit.create(SampleService::class.java)
    }
}