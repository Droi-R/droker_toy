package com.bvc.data.remote.api

import com.bvc.data.remote.model.request.OrderRequest
import com.bvc.data.remote.model.response.CategoryResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.MaterialsResponse
import com.bvc.data.remote.model.response.OrderResponse
import com.bvc.data.remote.model.response.ProductResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.SmartOrderResponse
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

    @GET("stores/details/{store_id}")
    suspend fun getStore(
        @Header("Authorization") token: String,
        @Path("store_id") storeId: String,
    ): Response<ResData<StoreResponse>>

    @GET("/stores/{store_id}/main-categories")
    suspend fun getMenuCategory(
        @Header("Authorization") token: String,
        @Path("store_id") storeId: String,
    ): Response<ResDataList<CategoryResponse>>

    @GET("/stores/{store_id}/main-categories/{main_category_id}/sub-categories")
    suspend fun getSubCategory(
        @Header("Authorization") token: String,
        @Path("store_id") storeId: String,
        @Path("main_category_id") mainCategoryId: String,
    ): Response<ResDataList<SubCategoryResponse>>

    @GET("stores/{store_id}/products")
    suspend fun getProducts(
        @Header("Authorization") token: String,
        @Path("store_id") storeId: String,
        @Query("main_category_id") mainCategoryId: String,
        @Query("sub_category_id") subCategoryId: String,
    ): Response<ResDataList<ProductResponse>>

    @GET("stores/{store_id}/products")
    suspend fun getMaterials(
        @Header("Authorization") token: String,
        @Path("store_id") storeId: String,
        @Query("main_category_id") mainCategoryId: String,
        @Query("sub_category_id") subCategoryId: String,
    ): Response<ResDataList<MaterialsResponse>>

    @GET("stores/{store_id}/smart-orders")
    suspend fun getSmartOrder(
        @Header("Authorization") token: String,
        @Path("store_id") storeId: String,
    ): Response<ResDataList<SmartOrderResponse>>

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
