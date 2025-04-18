package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.remote.api.MainApi
import com.bvc.data.remote.model.request.OrderRequest
import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.MaterialsResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.SmartOrderResponse
import com.bvc.data.remote.model.response.StoreResponse
import com.bvc.data.remote.model.response.SubCategoryResponse
import com.bvc.data.remote.model.response.TableResponse
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.data.utils.base.BaseRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainDataSourceImpl
    @Inject
    constructor(
        private val mainApi: MainApi,
    ) : BaseRepository(),
        MainDataSource {
        override suspend fun refreshToken(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ResData<LoginResponse>? =
            safeApiCall(remoteErrorEmitter) {
                mainApi
                    .refreshToken(
                        token = token,
                        refreshRequest = mapOf("refresh_token" to token),
                    ).body()
            }

        override suspend fun getStore(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
        ): ResData<StoreResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getStore(token, storeId).body() }

        override suspend fun getMenuCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
        ): ResDataList<CategoryResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getMenuCategory(token, storeId).body() }

        override suspend fun getSubCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
        ): ResDataList<SubCategoryResponse>? =
            safeApiCall(remoteErrorEmitter) { mainApi.getSubCategory(token, storeId, mainCategoryId).body() }

        override suspend fun getProducts(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ): ResDataList<ProductResponse>? =
            safeApiCall(remoteErrorEmitter) {
                mainApi.getProducts(token, storeId, mainCategoryId, subCategoryId).body()
            }

        override suspend fun getMaterials(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ): ResDataList<MaterialsResponse>? =
            safeApiCall(remoteErrorEmitter) {
                mainApi.getMaterials(token, storeId, mainCategoryId, subCategoryId).body()
            }

        override suspend fun getSmartOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
        ): ResDataList<SmartOrderResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getSmartOrder(token, storeId).body() }

        override suspend fun postOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
            orderRequest: OrderRequest,
        ): ResData<OrderResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.postOrder(token, id, orderRequest).body() }

        override suspend fun getTables(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ResDataList<TableResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getTables(token, id).body() }
    }
