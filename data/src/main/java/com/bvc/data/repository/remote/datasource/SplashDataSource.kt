package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.response.EmptyResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.StoreResponse

interface SplashDataSource {
    suspend fun sendSms(phoneNum: String): ResData<EmptyResponse>?

    suspend fun verifySms(
        phoneNum: String,
        verification: String,
    ): ResData<EmptyResponse>?

    suspend fun signUp(
        phoneNum: String,
        verification: String,
    ): ResData<LoginResponse>?

    suspend fun getLogin(
        phoneNum: String,
        verification: String,
    ): ResData<LoginResponse>?

    suspend fun getStore(
        token: String,
        id: String,
    ): ResDataList<StoreResponse>?
}
