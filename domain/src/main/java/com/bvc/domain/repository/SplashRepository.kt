package com.bvc.domain.repository

import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.EmptyEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.Store
import com.bvc.domain.utils.RemoteErrorEmitter

interface SplashRepository {
    suspend fun sendSms(
        remoteErrorEmitter: RemoteErrorEmitter,
        phoneNum: String,
    ): ApiData<EmptyEntity>?

    suspend fun verifySms(
        remoteErrorEmitter: RemoteErrorEmitter,
        phoneNum: String,
        verification: String,
    ): ApiData<EmptyEntity>?

    suspend fun signUp(
        remoteErrorEmitter: RemoteErrorEmitter,
        phoneNum: String,
        verification: String,
    ): ApiData<LoginEntity>?

    suspend fun getLogin(
        remoteErrorEmitter: RemoteErrorEmitter,
        phoneNum: String,
        verification: String,
    ): ApiData<LoginEntity>?

    suspend fun getStore(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        id: String,
    ): ApiDataList<Store>?
}
