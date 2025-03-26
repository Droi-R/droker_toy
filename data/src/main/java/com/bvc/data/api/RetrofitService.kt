package com.bvc.data.api

import com.bvc.data.model.YoReponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("users")
    suspend fun users(
        @Query("q") q: String,
    ): YoReponse.ResData
}
