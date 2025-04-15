package com.bvc.domain.usecase

import com.bvc.domain.model.ProductEntity
import com.bvc.domain.repository.MainRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentStatus
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

        suspend fun getStore(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
        ) = mainRepository.getStore(remoteErrorEmitter, token, storeId)

        suspend fun getMenuCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
        ) = mainRepository.getMenuCategory(remoteErrorEmitter, token, storeId)

        suspend fun getSubCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
        ) = mainRepository.getSubCategory(remoteErrorEmitter, token, storeId, mainCategoryId)

        suspend fun getProducts(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ) = mainRepository.getProducts(remoteErrorEmitter, token, storeId, mainCategoryId, subCategoryId)

        suspend fun getMaterials(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ) = mainRepository.getMaterials(remoteErrorEmitter, token, storeId, mainCategoryId, subCategoryId)

        suspend fun getSmartOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ) = mainRepository.getSmartOrder(remoteErrorEmitter, token, storeId)

        suspend fun postOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
            productItems: List<ProductEntity>,
            orderStatus: OrderStatus,
            paymentStatus: PaymentStatus,
            orderFrom: OrderFrom,
            tableNumber: String,
            tableExternalKey: String,
        ) = mainRepository.postOrder(
            remoteErrorEmitter = remoteErrorEmitter,
            token = token,
            id = id,
            productItems = productItems,
            orderStatus = orderStatus,
            paymentStatus = paymentStatus,
            orderFrom = orderFrom,
            tableNumber = tableNumber,
            tableExternalKey = tableExternalKey,
        )

        suspend fun getTables(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ) = mainRepository.getTables(remoteErrorEmitter, token, id)
    }
