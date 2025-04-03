package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.remote.api.MainApi
import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.CategoryResponse
import com.bvc.data.remote.model.ProductResponse
import com.bvc.data.remote.model.ResDataList
import com.bvc.data.remote.model.SubCategoryResponse
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
    }
