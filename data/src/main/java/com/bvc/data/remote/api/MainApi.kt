package com.bvc.data.remote.api

import com.bvc.data.remote.model.request.OrderRequest
import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.StoreResponse
import com.bvc.data.remote.model.response.SubCategoryResponse
import com.bvc.data.remote.model.response.TableResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @POST("auth/send-sms")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
        @Body refreshRequest: Map<String, String>,
    ): Response<ResData<LoginResponse>>

    @GET("users/{token}/repos")
    suspend fun getAffiliate(
        @Header("Authorization") token: String,
    ): Response<ResDataList<StoreResponse>>

    @GET("users/{token}/repos")
    suspend fun getMenuCategory(
        @Header("Authorization") token: String,
    ): Response<ResDataList<CategoryResponse>>

    @GET("users/{token}/repos")
    suspend fun getSubCategory(
        @Header("Authorization") token: String,
        @Query("id") id: String,
    ): Response<ResDataList<SubCategoryResponse>>

    @GET("users/{token}/repos")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Query("id") id: String,
    ): Response<ResDataList<ProductResponse>>

    @POST("users/{sid}/repos")
    suspend fun postOrder(
        @Header("Authorization") token: String,
        @Path("sid") sid: String,
        @Body orderRequest: OrderRequest,
    ): Response<ResData<OrderResponse>>

    @GET("users/{token}/repos")
    suspend fun getTables(
        @Header("Authorization") token: String,
        @Query("id") id: String,
    ): Response<ResDataList<TableResponse>>
}
