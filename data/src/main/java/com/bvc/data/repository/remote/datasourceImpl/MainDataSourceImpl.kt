package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.mapper.toRequest
import com.bvc.data.remote.api.MainApi
import com.bvc.data.remote.model.request.OrderRequest
import com.bvc.data.remote.model.response.AffiliateResponse
import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.SubCategoryResponse
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.data.utils.base.BaseRepository
import com.bvc.domain.model.CartEntity
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainDataSourceImpl
    @Inject
    constructor(
        private val mainApi: MainApi,
    ) : BaseRepository(),
        MainDataSource {
        override suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ResDataList<AffiliateResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getAffiliate(token).body() }

        override suspend fun getMenuCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ResDataList<CategoryResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getMenuCategory(token).body() }

        override suspend fun getSubCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ResDataList<SubCategoryResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getSubCategory(token, id).body() }

        override suspend fun getProducts(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ResDataList<ProductResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getProducts(token, id).body() }

        override suspend fun postOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
            productItems: List<CartEntity>,
            status: OrderStatus,
            orderFrom: OrderFrom,
            tableNumber: String,
            tableExternalKey: String,
        ): ResData<OrderResponse>? {
            val orderReqest =
                OrderRequest(
                    productItems = productItems.map { it.toRequest() },
                    status = status,
                    orderFrom = orderFrom,
                    tableNumber = tableNumber,
                    tableExternalKey = tableExternalKey,
                )
            return safeApiCall(remoteErrorEmitter) { mainApi.postOrder(token, id, orderReqest).body() }
        }
    }
