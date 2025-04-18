package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.request.CaptureRequest
import com.bvc.data.remote.model.request.OrderRequest
import com.bvc.data.remote.model.request.PaymentRequest
import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.EmptyResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.MaterialsResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.PaymentResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.SmartOrderResponse
import com.bvc.data.remote.model.response.StoreResponse
import com.bvc.data.remote.model.response.SubCategoryResponse
import com.bvc.data.remote.model.response.TableResponse

interface MainDataSource {
    suspend fun refreshToken(token: String): ResData<LoginResponse>?

    suspend fun getStore(
        token: String,
        storeId: String,
    ): ResData<StoreResponse>?

    suspend fun getMenuCategory(
        token: String,
        storeId: String,
    ): ResDataList<CategoryResponse>?

    suspend fun getSubCategory(
        token: String,
        storeId: String,
        mainCategoryId: String,
    ): ResDataList<SubCategoryResponse>?

    suspend fun getProducts(
        token: String,
        storeId: String,
        mainCategoryId: String,
        subCategoryId: String,
    ): ResDataList<ProductResponse>?

    suspend fun getMaterials(
        token: String,
        storeId: String,
        mainCategoryId: String,
        subCategoryId: String,
    ): ResDataList<MaterialsResponse>?

    suspend fun getSmartOrder(
        token: String,
        storeId: String,
    ): ResDataList<SmartOrderResponse>?

    suspend fun postOrder(
        token: String,
        orderRequest: OrderRequest,
    ): ResData<OrderResponse>?

    suspend fun postPayment(
        token: String,
        paymentRequest: PaymentRequest,
    ): ResData<PaymentResponse>?

    suspend fun postCapture(
        token: String,
        captureRequest: CaptureRequest,
    ): ResData<EmptyResponse>?

    suspend fun getTables(
        token: String,
        id: String,
    ): ResDataList<TableResponse>?
}
