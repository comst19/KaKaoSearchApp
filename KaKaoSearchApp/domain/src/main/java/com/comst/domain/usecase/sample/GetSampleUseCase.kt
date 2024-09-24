package com.comst.domain.usecase.sample

import com.comst.domain.repository.SampleRepository
import javax.inject.Inject

class GetSampleUseCase @Inject constructor(
    private val sampleRepository: SampleRepository
){
    suspend operator fun invoke() = runCatching {
        sampleRepository.getSample()
    }
}