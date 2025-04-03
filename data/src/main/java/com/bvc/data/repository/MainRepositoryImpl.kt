package com.bvc.data.repository

import com.bvc.data.mapper.Mapper
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.DataList
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
import com.bvc.domain.repository.MainRepository
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
        ): DataList<AffiliateEntity> = Mapper.mapAffiliate(mainDataSource.getAffiliate(remoteErrorEmitter, token))

        override suspend fun getMenuCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): DataList<CategoryEntity> = Mapper.mapCategory(mainDataSource.getMenuCategory(remoteErrorEmitter, token))

        override suspend fun getSubCategory(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): DataList<SubCategoryEntity> = Mapper.mapSubCategory(mainDataSource.getSubCategory(remoteErrorEmitter, token, id))

        override suspend fun getProducts(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): DataList<ProductEntity> = Mapper.mapProduct(mainDataSource.getProducts(remoteErrorEmitter, token, id))
    }
