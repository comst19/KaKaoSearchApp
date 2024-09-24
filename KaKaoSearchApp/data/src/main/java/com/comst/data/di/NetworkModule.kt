package com.comst.data.di

import com.comst.data.BuildConfig
import com.comst.data.BuildConfig.BASE_URL
import com.comst.data.network.ApiResultCallAdapterFactory
import com.comst.data.network.TokenAuthenticator
import com.comst.data.network.TokenInterceptor
import com.comst.data.util.LocalDateAdapter
import com.comst.data.util.LocalDateTimeAdapter
import com.comst.data.util.UnitJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PublicOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PublicRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthRetrofit

    @Provides
    @Singleton
    fun moshi(): Moshi =
        Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .add(LocalDateAdapter())
            .add(UnitJsonAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    @PublicOkHttpClient
    fun provideUnauthenticatedOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAccessOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .authenticator(tokenAuthenticator)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @PublicRetrofit
    fun providePublicRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        @PublicOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        @AuthOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }

}