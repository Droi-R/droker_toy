package com.bvc.data.remote.api

import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.CategoryResponse
import com.bvc.data.remote.model.ProductResponse
import com.bvc.data.remote.model.ResDataList
import com.bvc.data.remote.model.SubCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("users/{token}/repos")
    suspend fun getAffiliate(
        @Path("token") token: String,
    ): Response<ResDataList<AffiliateResponse>>

    @GET("users/{token}/repos")
    suspend fun getMenuCategory(
        @Path("token") token: String,
    ): Response<ResDataList<CategoryResponse>>

    @GET("users/{token}/repos")
    suspend fun getSubCategory(
        @Path("token") token: String,
        @Query("id") id: String,
    ): Response<ResDataList<SubCategoryResponse>>

    @GET("users/{token}/repos")
    suspend fun getProducts(
        @Path("token") token: String,
        @Query("id") id: String,
    ): Response<ResDataList<ProductResponse>>
}
