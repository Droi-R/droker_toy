package com.bvc.data.remote.api

import com.bvc.data.remote.model.AffiliateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApi {
    @GET("users/{token}/repos")
    suspend fun getAffiliate(
        @Path("token") token: String,
    ): Response<List<AffiliateResponse>>
}
