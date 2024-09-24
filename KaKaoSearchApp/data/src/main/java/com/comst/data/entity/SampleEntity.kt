package com.comst.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.comst.model.Sample

@Entity
data class SampleEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)

internal fun SampleEntity.toDomainModel() = Sample(
    title = title,
    content = content
)