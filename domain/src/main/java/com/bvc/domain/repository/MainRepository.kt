package com.bvc.domain.repository

import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.utils.RemoteErrorEmitter

interface MainRepository {
    suspend fun refreshToken(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ApiData<LoginEntity>

    suspend fun getAffiliate(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ApiDataList<AffiliateEntity>

    suspend fun getMenuCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ApiDataList<CategoryEntity>

    suspend fun getSubCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ApiDataList<SubCategoryEntity>

    suspend fun getProducts(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ApiDataList<ProductEntity>

    suspend fun postOrder(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
        productItems: List<ProductEntity>,
        status: OrderStatus,
        orderFrom: OrderFrom,
        tableNumber: String,
        tableExternalKey: String,
    ): ApiData<OrderEntity>

    suspend fun getTables(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ApiDataList<TableEntity>
}
