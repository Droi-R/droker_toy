package com.bvc.domain.usecase

import com.bvc.domain.model.ProductEntity
import com.bvc.domain.repository.MainRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentChannel
import com.bvc.domain.type.PaymentMethod
import com.bvc.domain.type.PaymentStatus
import com.bvc.domain.type.PaymentType
import javax.inject.Inject

class MainUseCase
    @Inject
    constructor(
        private val mainRepository: MainRepository,
    ) {
        suspend fun refreshToken(refreshToken: String) = mainRepository.refreshToken(refreshToken)

        suspend fun getStore(
            token: String,
            storeId: String,
        ) = mainRepository.getStore(token, storeId)

        suspend fun getMenuCategory(
            token: String,
            storeId: String,
        ) = mainRepository.getMenuCategory(token, storeId)

        suspend fun getTableArea(
            token: String,
            storeId: String,
        ) = mainRepository.getTableArea(token, storeId)

        suspend fun getSubCategory(
            token: String,
            storeId: String,
            mainCategoryId: String,
        ) = mainRepository.getSubCategory(token, storeId, mainCategoryId)

        suspend fun getProducts(
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ) = mainRepository.getProducts(token, storeId, mainCategoryId, subCategoryId)

        suspend fun getMaterials(
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ) = mainRepository.getMaterials(token, storeId, mainCategoryId, subCategoryId)

        suspend fun getSmartOrder(
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ) = mainRepository.getSmartOrder(token, storeId)

        suspend fun postOrder(
            token: String,
            userId: String,
            storeId: String,
            productItems: List<ProductEntity>,
            orderFrom: OrderFrom,
            orderStatus: OrderStatus,
            tablesId: Int,
            itemMemo: String,
            totalPrice: Int,
            supplyPrice: Int,
            vatPrice: Int,
            discountPrice: Int,
        ) = mainRepository.postOrder(
            token,
            userId,
            storeId,
            productItems,
            orderFrom,
            orderStatus,
            tablesId,
            itemMemo,
            totalPrice,
            supplyPrice,
            vatPrice,
            discountPrice,
        )

        suspend fun postPayment(
            token: String,
            userId: String,
            storeId: String,
            orderProductIds: List<String>,
            totalPrice: String,
            paymentMethod: PaymentMethod,
            paymentChannel: PaymentChannel,
            paymentStatus: PaymentStatus,
            paymentType: PaymentType,
        ) = mainRepository.postPayment(
            token = token,
            userId = userId,
            storeId = storeId,
            orderProductIds = orderProductIds,
            totalPrice = totalPrice,
            paymentMethod = paymentMethod,
            paymentChannel = paymentChannel,
            paymentStatus = paymentStatus,
            paymentType = paymentType,
        )

        suspend fun requestCapture(
            token: String,
            paymentId: String,
            amount: String,
            deviceId: String,
            approvedId: String,
            approvedDate: String,
        ) = mainRepository.requestCapture(
            token = token,
            paymentId = paymentId,
            amount = amount,
            deviceId = deviceId,
            approvedId = approvedId,
            approvedDate = approvedDate,
        )

        suspend fun reqeustRefund(
            token: String,
            paymentId: String,
            amount: String,
            deviceId: String,
            approvedId: String,
            approvedDate: String,
            refundApprovedId: String,
            refundApprovedDate: String,
        ) = mainRepository.requestRefund(
            token = token,
            paymentId = paymentId,
            amount = amount,
            deviceId = deviceId,
            approvedId = approvedId,
            approvedDate = approvedDate,
            refundApprovedId = refundApprovedId,
            refundApprovedDate = refundApprovedDate,
        )

        suspend fun getTables(
            token: String,
            id: String,
        ) = mainRepository.getTables(token, id)
    }
