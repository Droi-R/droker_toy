package com.bvc.data.remote.api

import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.LoginResponse
import com.bvc.data.remote.model.ResData
import com.bvc.data.remote.model.ResDataList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SplashApi {
    @GET("users/{token}/repos")
    suspend fun getLogin(
        @Path("token") token: String,
    ): Response<ResData<LoginResponse>>

    @GET("users/{token}/repos")
    suspend fun getAffiliate(
        @Path("token") token: String,
    ): Response<ResDataList<AffiliateResponse>>
}
