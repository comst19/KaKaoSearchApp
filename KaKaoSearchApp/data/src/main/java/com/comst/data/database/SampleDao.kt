package com.comst.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.comst.data.entity.SampleEntity

@Dao
interface SampleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(samples:List<SampleEntity>)

    @Query("DELETE FROM sampleentity")
    fun deleteAll()

    @Query("SELECT * FROM sampleentity ORDER BY id DESC")
    fun getPagingAll(): PagingSource<Int, SampleEntity>

    @Query("SELECT * FROM sampleentity ORDER BY id DESC")
    fun getAll(): List<SampleEntity>
}