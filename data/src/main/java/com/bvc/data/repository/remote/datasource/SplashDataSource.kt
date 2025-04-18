package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.response.EmptyResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.remote.model.response.StoreResponse
import com.bvc.domain.utils.RemoteErrorEmitter

interface SplashDataSource {
    suspend fun sendSms(
        remoteErrorEmitter: RemoteErrorEmitter,
        phoneNum: String,
    ): ResData<EmptyResponse>?

    suspend fun verifySms(
        remoteErrorEmitter: RemoteErrorEmitter,
        phoneNum: String,
        verification: String,
    ): ResData<EmptyResponse>?

    suspend fun signUp(
        remoteErrorEmitter: RemoteErrorEmitter,
        phoneNum: String,
        verification: String,
    ): ResData<LoginResponse>?

    suspend fun getLogin(
        remoteErrorEmitter: RemoteErrorEmitter,
        phoneNum: String,
        verification: String,
    ): ResData<LoginResponse>?

    suspend fun getStore(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ResDataList<StoreResponse>?
}
