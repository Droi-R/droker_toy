package com.bvc.domain.repository

import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.CartEntity
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.Data
import com.bvc.domain.model.DataList
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.utils.RemoteErrorEmitter

interface MainRepository {
    suspend fun getAffiliate(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): DataList<AffiliateEntity>

    suspend fun getMenuCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): DataList<CategoryEntity>

    suspend fun getSubCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): DataList<SubCategoryEntity>

    suspend fun getProducts(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): DataList<ProductEntity>

    suspend fun postOrder(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
        productItems: List<CartEntity>,
        status: OrderStatus,
        orderFrom: OrderFrom,
        tableNumber: String,
        tableExternalKey: String,
    ): Data<OrderEntity>
}
