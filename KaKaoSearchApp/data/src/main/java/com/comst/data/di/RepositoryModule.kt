package com.comst.data.di

import com.comst.data.repository.download.DownloadRepositoryImpl
import com.comst.data.repository.kakao.favorite.FavoriteRepositoryImpl
import com.comst.data.repository.kakao.search.KaKaoSearchRepositoryImpl
import com.comst.data.repository.token.TokenRepositoryImpl
import com.comst.data.repository.sample.SampleRepositoryImpl
import com.comst.domain.repository.DownloadRepository
import com.comst.domain.repository.FavoriteRepository
import com.comst.domain.repository.KaKaoSearchRepository
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

    @Singleton
    @Binds
    abstract fun bindKaKaoSearchRepository(
        kaKaoSearchRepository: KaKaoSearchRepositoryImpl
    ): KaKaoSearchRepository

    @Singleton
    @Binds
    abstract fun bindFavoriteRepository(
        favoriteRepository: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Singleton
    @Binds
    abstract fun bindDownloadRepository(
        downloadRepository: DownloadRepositoryImpl
    ): DownloadRepository
}