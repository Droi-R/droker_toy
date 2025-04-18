package com.bvc.data.remote.api

import com.bvc.data.remote.model.request.SignUpRequest
import com.bvc.data.remote.model.request.VerifySmsRequest
import com.bvc.data.remote.model.response.EmptyResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.StoreResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SplashApi {
    @POST("auth/send-sms")
    suspend fun sendSms(
        @Body sendSms: Map<String, String>,
    ): Response<ResData<EmptyResponse>>

    @POST("auth/verify-sms")
    suspend fun verifySms(
        @Body verifySmsRequest: VerifySmsRequest,
    ): Response<ResData<EmptyResponse>>

    @POST("auth/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest,
    ): Response<ResData<LoginResponse>>

    @POST("auth/login")
    suspend fun login(
        @Body verifySmsRequest: VerifySmsRequest,
    ): Response<ResData<LoginResponse>>

    @GET("stores/{id}")
    suspend fun getStore(
        @Header("Authorization") Token: String,
        @Path("id") id: String,
    ): Response<ResDataList<StoreResponse>>
}
