package com.bvc.domain.repository

import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.EmptyEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.PaymentEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SmartOrderEntity
import com.bvc.domain.model.Store
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentChannel
import com.bvc.domain.type.PaymentMethod
import com.bvc.domain.type.PaymentStatus
import com.bvc.domain.type.PaymentType

interface MainRepository {
    suspend fun refreshToken(token: String): ApiData<LoginEntity>

    suspend fun getStore(
        token: String,
        storeId: String,
    ): ApiData<Store>

    suspend fun getMenuCategory(
        token: String,
        storeId: String,
    ): ApiDataList<CategoryEntity>

    suspend fun getSubCategory(
        token: String,
        storeId: String,
        mainCategoryId: String,
    ): ApiDataList<SubCategoryEntity>

    suspend fun getProducts(
        token: String,
        storeId: String,
        mainCategoryId: String,
        subCategoryId: String,
    ): ApiDataList<ProductEntity>

    suspend fun getMaterials(
        token: String,
        storeId: String,
        mainCategoryId: String,
        subCategoryId: String,
    ): ApiDataList<MaterialsEntity>

    suspend fun getSmartOrder(
        token: String,
        storeId: String,
    ): ApiDataList<SmartOrderEntity>

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
    ): ApiData<OrderEntity>

    suspend fun postPayment(
        token: String,
        userId: String,
        storeId: String,
        orderProductIds: List<String>,
        totalPrice: Int,
        paymentMethod: PaymentMethod,
        paymentChannel: PaymentChannel,
        paymentStatus: PaymentStatus,
        paymentType: PaymentType,
    ): ApiData<PaymentEntity>

    suspend fun requestCapture(
        token: String,
        paymentId: String,
        amount: Double,
        deviceId: String,
        approvedId: String,
        approvedDate: String,
    ): ApiData<EmptyEntity>

    suspend fun requestRefund(
        token: String,
        paymentId: String,
        amount: Double,
        deviceId: String,
        approvedId: String,
        approvedDate: String,
    ): ApiData<EmptyEntity>

    suspend fun getTables(
        token: String,
        id: String,
    ): ApiDataList<TableEntity>
}
