package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.StoreResponse
import com.bvc.data.remote.model.response.SubCategoryResponse
import com.bvc.data.remote.model.response.TableResponse
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.utils.RemoteErrorEmitter

interface MainDataSource {
    suspend fun refreshToken(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ResData<LoginResponse>?

    suspend fun getAffiliate(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ResDataList<StoreResponse>?

    suspend fun getMenuCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ResDataList<CategoryResponse>?

    suspend fun getSubCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ResDataList<SubCategoryResponse>?

    suspend fun getProducts(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ResDataList<ProductResponse>?

    suspend fun postOrder(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
        productItems: List<ProductEntity>,
        status: OrderStatus,
        orderFrom: OrderFrom,
        tableNumber: String,
        tableExternalKey: String,
    ): ResData<OrderResponse>?

    suspend fun getTables(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ResDataList<TableResponse>?
}
