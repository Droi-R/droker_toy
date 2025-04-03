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

        suspend fun getMenuCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ) = mainRepository.getMenuCategory(remoteErrorEmitter, token)

        suspend fun getSubCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ) = mainRepository.getSubCategory(remoteErrorEmitter, token, id)

        suspend fun getProducts(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ) = mainRepository.getProducts(remoteErrorEmitter, token, id)
    }
