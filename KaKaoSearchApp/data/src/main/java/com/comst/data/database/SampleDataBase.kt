package com.comst.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.comst.data.entity.SampleEntity

@Database(
    entities = [SampleEntity::class],
    version = 1
)
abstract class SampleDataBase: RoomDatabase() {
    abstract fun SampleDao(): SampleDao
}