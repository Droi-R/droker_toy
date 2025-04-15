package com.bvc.data.repository.remote.datasource

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
import com.bvc.domain.utils.RemoteErrorEmitter

interface MainDataSource {
    suspend fun refreshToken(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ResData<LoginResponse>?

    suspend fun getStore(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        storeId: String,
    ): ResData<StoreResponse>?

    suspend fun getMenuCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        storeId: String,
    ): ResDataList<CategoryResponse>?

    suspend fun getSubCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        storeId: String,
        mainCategoryId: String,
    ): ResDataList<SubCategoryResponse>?

    suspend fun getProducts(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        storeId: String,
        mainCategoryId: String,
        subCategoryId: String,
    ): ResDataList<ProductResponse>?

    suspend fun getMaterials(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        storeId: String,
        mainCategoryId: String,
        subCategoryId: String,
    ): ResDataList<MaterialsResponse>?

    suspend fun getSmartOrder(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        storeId: String,
    ): ResDataList<SmartOrderResponse>?

    suspend fun postOrder(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
        orderRequest: OrderRequest,
    ): ResData<OrderResponse>?

    suspend fun getTables(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ResDataList<TableResponse>?
}
