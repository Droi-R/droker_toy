package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.remote.api.MainApi
import com.bvc.data.remote.model.request.ApproveRequest
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
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.data.utils.base.BaseRepository
import javax.inject.Inject

class MainDataSourceImpl
    @Inject
    constructor(
        private val mainApi: MainApi,
    ) : BaseRepository(),
        MainDataSource {
        override suspend fun refreshToken(token: String): ResData<LoginResponse>? =
            safeApiCall {
                mainApi.refreshToken(
                    token = token,
                    refreshRequest = mapOf("refresh_token" to token),
                )
            }.body()

        override suspend fun getStore(
            token: String,
            storeId: String,
        ): ResData<StoreResponse>? =
            safeApiCall {
                mainApi.getStore(token, storeId)
            }.body()

        override suspend fun getMenuCategory(
            token: String,
            storeId: String,
        ): ResDataList<CategoryResponse>? =
            safeApiCall {
                mainApi.getMenuCategory(token, storeId)
            }.body()

        override suspend fun getSubCategory(
            token: String,
            storeId: String,
            mainCategoryId: String,
        ): ResDataList<SubCategoryResponse>? =
            safeApiCall {
                mainApi.getSubCategory(token, storeId, mainCategoryId)
            }.body()

        override suspend fun getProducts(
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ): ResDataList<ProductResponse>? =
            safeApiCall {
                mainApi.getProducts(token, storeId, mainCategoryId, subCategoryId)
            }.body()

        override suspend fun getMaterials(
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ): ResDataList<MaterialsResponse>? =
            safeApiCall {
                mainApi.getMaterials(token, storeId, mainCategoryId, subCategoryId)
            }.body()

        override suspend fun getSmartOrder(
            token: String,
            storeId: String,
        ): ResDataList<SmartOrderResponse>? =
            safeApiCall {
                mainApi.getSmartOrder(token, storeId)
            }.body()

        override suspend fun postOrder(
            token: String,
            orderRequest: OrderRequest,
        ): ResData<OrderResponse>? =
            safeApiCall {
                mainApi.postOrder(token, orderRequest)
            }.body()

        override suspend fun postPayment(
            token: String,
            paymentRequest: PaymentRequest,
        ): ResData<PaymentResponse>? =
            safeApiCall {
                mainApi.postPayment(token, paymentRequest)
            }.body()

        override suspend fun postCapture(
            token: String,
            captureRequest: ApproveRequest,
        ): ResData<EmptyResponse>? =
            safeApiCall {
                mainApi.postCapture(token, captureRequest)
            }.body()

        override suspend fun postRefund(
            token: String,
            refundRequest: ApproveRequest,
        ): ResData<EmptyResponse>? =
            safeApiCall {
                mainApi.postRefund(token, refundRequest)
            }.body()

        override suspend fun getTables(
            token: String,
            id: String,
        ): ResDataList<TableResponse>? =
            safeApiCall {
                mainApi.getTables(token, id)
            }.body()
    }
