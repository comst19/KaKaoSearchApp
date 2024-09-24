package com.comst.data.di

import android.content.Context
import androidx.room.Room
import com.comst.data.database.SampleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBaseModule {

    companion object {

        @Provides
        fun provideSampleDataBase(context: Context): SampleDataBase {
            return Room.databaseBuilder(
                context,
                SampleDataBase::class.java,
                "SampleDataBase"
            ).build()
        }
    }
}