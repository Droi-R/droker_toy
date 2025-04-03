package com.bvc.domain.repository

import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.CategoryEntity
import com.bvc.domain.model.DataList
import com.bvc.domain.model.ProductEntity
import com.bvc.domain.model.SubCategoryEntity
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
}
