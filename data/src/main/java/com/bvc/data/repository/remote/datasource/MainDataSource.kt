package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.CategoryResponse
import com.bvc.data.remote.model.ProductResponse
import com.bvc.data.remote.model.ResDataList
import com.bvc.data.remote.model.SubCategoryResponse
import com.bvc.domain.utils.RemoteErrorEmitter

interface MainDataSource {
    suspend fun getAffiliate(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ResDataList<AffiliateResponse>?

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
}
