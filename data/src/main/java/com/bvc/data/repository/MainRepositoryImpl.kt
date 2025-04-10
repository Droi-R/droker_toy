package com.bvc.data.repository

import com.bvc.data.mapper.ResponseMapper
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
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
        override suspend fun refreshToken(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ApiData<LoginEntity> = ResponseMapper.mapRefreshToken(mainDataSource.refreshToken(remoteErrorEmitter, token))

        override suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ApiDataList<AffiliateEntity> = ResponseMapper.mapAffiliate(mainDataSource.getAffiliate(remoteErrorEmitter, token))

        override suspend fun getMenuCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ApiDataList<CategoryEntity> = ResponseMapper.mapCategory(mainDataSource.getMenuCategory(remoteErrorEmitter, token))

        override suspend fun getSubCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ApiDataList<SubCategoryEntity> = ResponseMapper.mapSubCategory(mainDataSource.getSubCategory(remoteErrorEmitter, token, id))

        override suspend fun getProducts(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ApiDataList<ProductEntity> = ResponseMapper.mapProducts(mainDataSource.getProducts(remoteErrorEmitter, token, id))

        override suspend fun postOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
            productItems: List<ProductEntity>,
            status: OrderStatus,
            orderFrom: OrderFrom,
            tableNumber: String,
            tableExternalKey: String,
        ): ApiData<OrderEntity> =
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

        override suspend fun getTables(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ApiDataList<TableEntity> = ResponseMapper.mapTables(mainDataSource.getTables(remoteErrorEmitter, token, id))
    }
