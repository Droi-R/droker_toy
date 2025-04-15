package com.bvc.data.repository

import com.bvc.data.mapper.ResponseMapper
import com.bvc.data.mapper.toRequest
import com.bvc.data.remote.model.request.OrderRequest
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.MaterialsEntity
import com.bvc.domain.model.OrderEntity
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SmartOrderEntity
import com.bvc.domain.model.Store
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.model.TableEntity
import com.bvc.domain.repository.MainRepository
import com.bvc.domain.type.OrderFrom
import com.bvc.domain.type.OrderStatus
import com.bvc.domain.type.PaymentStatus
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

        override suspend fun getStore(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
        ): ApiData<Store> = ResponseMapper.mapStore(mainDataSource.getStore(remoteErrorEmitter, token, storeId))

        override suspend fun getMenuCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
        ): ApiDataList<CategoryEntity> = ResponseMapper.mapCategory(mainDataSource.getMenuCategory(remoteErrorEmitter, token, storeId))

        override suspend fun getSubCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
        ): ApiDataList<SubCategoryEntity> =
            ResponseMapper.mapSubCategory(mainDataSource.getSubCategory(remoteErrorEmitter, token, storeId, mainCategoryId))

        override suspend fun getProducts(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ): ApiDataList<ProductEntity> =
            ResponseMapper.mapProducts(mainDataSource.getProducts(remoteErrorEmitter, token, storeId, mainCategoryId, subCategoryId))

        override suspend fun getMaterials(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
            mainCategoryId: String,
            subCategoryId: String,
        ): ApiDataList<MaterialsEntity> =
            ResponseMapper.mapMaterials(mainDataSource.getMaterials(remoteErrorEmitter, token, storeId, mainCategoryId, subCategoryId))

        override suspend fun getSmartOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            storeId: String,
        ): ApiDataList<SmartOrderEntity> = ResponseMapper.mapSmartOrder(mainDataSource.getSmartOrder(remoteErrorEmitter, token, storeId))

        override suspend fun postOrder(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
            productItems: List<ProductEntity>,
            orderStatus: OrderStatus,
            paymentStatus: PaymentStatus,
            orderFrom: OrderFrom,
            tableNumber: String,
            tableExternalKey: String,
        ): ApiData<OrderEntity> =
            ResponseMapper.mapOrder(
                mainDataSource.postOrder(
                    remoteErrorEmitter = remoteErrorEmitter,
                    token = token,
                    id = id,
                    orderRequest =
                        OrderRequest(
                            productItems = productItems.map { it.toRequest() },
                            orderStatus = orderStatus,
                            paymentStatus = paymentStatus,
                            orderFrom = orderFrom,
                            tableNumber = tableNumber,
                            tableExternalKey = tableExternalKey,
                        ),
                ),
            )

        override suspend fun getTables(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ApiDataList<TableEntity> = ResponseMapper.mapTables(mainDataSource.getTables(remoteErrorEmitter, token, id))
    }
