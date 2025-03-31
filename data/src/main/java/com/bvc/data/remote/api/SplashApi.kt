package com.bvc.data.remote.api

import com.bvc.data.remote.model.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SplashApi {
    @GET("users/{token}/repos")
    suspend fun getLogin(
        @Path("token") token: String,
    ): Response<List<LoginResponse>>
}
