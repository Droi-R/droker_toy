package com.bvc.domain.usecase

import com.bvc.domain.repository.MainRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainUseCase
    @Inject
    constructor(
        private val mainRepository: MainRepository,
    ) {
        suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ) = mainRepository.getAffiliate(remoteErrorEmitter, token)
    }
