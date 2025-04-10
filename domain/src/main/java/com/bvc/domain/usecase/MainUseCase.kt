package com.bvc.domain.usecase

import com.bvc.domain.model.ProductEntity
import com.bvc.domain.repository.MainRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainUseCase
    @Inject
    constructor(
        private val mainRepository: MainRepository,
    ) {
        suspend fun refreshToken(
            remoteErrorEmitter: RemoteErrorEmitter,
            refreshToken: String,
        ) = mainRepository.refreshToken(remoteErrorEmitter, refreshToken)

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

        suspend fun postOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
            productItems: List<ProductEntity>,
            status: OrderStatus,
            orderFrom: OrderFrom,
            tableNumber: String,
            tableExternalKey: String,
        ) = mainRepository.postOrder(
            remoteErrorEmitter,
            token,
            id,
            productItems,
            status,
            orderFrom,
            tableNumber,
            tableExternalKey,
        )

        suspend fun getTables(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ) = mainRepository.getTables(remoteErrorEmitter, token, id)
    }
