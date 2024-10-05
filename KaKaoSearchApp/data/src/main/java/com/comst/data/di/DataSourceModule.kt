package com.comst.data.di

import com.comst.data.repository.kakao.favorite.local.FavoriteLocalDataSource
import com.comst.data.repository.kakao.favorite.local.FavoriteLocalDataSourceImpl
import com.comst.data.repository.kakao.search.remote.KaKaoSearchRemoteDataSource
import com.comst.data.repository.kakao.search.remote.KaKaoSearchRemoteDataSourceImpl
import com.comst.data.repository.sample.remote.SampleRemoteDataSource
import com.comst.data.repository.sample.remote.SampleRemoteDataSourceImpl
import com.comst.data.repository.token.local.TokenLocalDataSource
import com.comst.data.repository.token.local.TokenLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindSampleRemoteDataSource(
        sampleRemoteDataSource: SampleRemoteDataSourceImpl
    ): SampleRemoteDataSource

    @Binds
    abstract fun bindTokenLocalDataSource(
        tokenLocalDataSource: TokenLocalDataSourceImpl
    ): TokenLocalDataSource

    @Binds
    abstract fun bindKaKaoSearchRemoteDataSource(
        kaKaoSearchRemoteDataSource: KaKaoSearchRemoteDataSourceImpl
    ): KaKaoSearchRemoteDataSource

    @Binds
    abstract fun bindFavoriteLocalDataSource(
        favoriteLocalDataSource: FavoriteLocalDataSourceImpl
    ): FavoriteLocalDataSource
}