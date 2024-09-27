package com.comst.data.di

import com.comst.data.BuildConfig.KAKAO_BASE_URL
import com.comst.data.network.ApiResultCallAdapterFactory
import com.comst.data.network.MoshiProvider
import com.comst.data.network.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshiProvider(): MoshiProvider = MoshiProvider

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshiProvider: MoshiProvider): MoshiConverterFactory =
        MoshiConverterFactory.create(moshiProvider.moshi)

    @Provides
    @Singleton
    fun provideKaKaoOkHttpClient(
        tokenInterceptor: TokenInterceptor,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideKaKaoRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(KAKAO_BASE_URL)
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }

}