package com.comst.data.dto.sample.response

import com.comst.data.entity.SampleEntity
import com.comst.model.Sample

data class SampleResponse(
    val title: String,
    val content: String
)
internal fun SampleResponse.toDomainModel() = Sample(
    title = title,
    content = content
)

internal fun SampleResponse.toEntity() = SampleEntity(
    title = title,
    content = content
)