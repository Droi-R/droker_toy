package com.bvc.data.repository

import com.bvc.data.mapper.ResponseMapper
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.CartEntity
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.Data
import com.bvc.domain.model.DataList
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.repository.MainRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainRepositoryImpl
    @Inject
    constructor(
        private val mainDataSource: MainDataSource,
    ) : MainRepository {
        override suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): DataList<AffiliateEntity> = ResponseMapper.mapAffiliate(mainDataSource.getAffiliate(remoteErrorEmitter, token))

        override suspend fun getMenuCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): DataList<CategoryEntity> = ResponseMapper.mapCategory(mainDataSource.getMenuCategory(remoteErrorEmitter, token))

        override suspend fun getSubCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): DataList<SubCategoryEntity> = ResponseMapper.mapSubCategory(mainDataSource.getSubCategory(remoteErrorEmitter, token, id))

        override suspend fun getProducts(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): DataList<ProductEntity> = ResponseMapper.mapProducts(mainDataSource.getProducts(remoteErrorEmitter, token, id))

        override suspend fun postOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
            productItems: List<CartEntity>,
            status: OrderStatus,
            orderFrom: OrderFrom,
            tableNumber: String,
            tableExternalKey: String,
        ): Data<OrderEntity> =
            ResponseMapper.mapOrder(
                mainDataSource.postOrder(
                    remoteErrorEmitter = remoteErrorEmitter,
                    token = token,
                    id = id,
                    productItems = productItems,
                    status = status,
                    orderFrom = orderFrom,
                    tableNumber = tableNumber,
                    tableExternalKey = tableExternalKey,
                ),
            )
    }
