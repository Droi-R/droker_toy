package com.bvc.domain.repository

import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.EmptyEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.Store

interface SplashRepository {
    suspend fun sendSms(phoneNum: String): ApiData<EmptyEntity>?

    suspend fun verifySms(
        phoneNum: String,
        verification: String,
    ): ApiData<EmptyEntity>?

    suspend fun signUp(
        phoneNum: String,
        verification: String,
    ): ApiData<LoginEntity>?

    suspend fun getLogin(
        phoneNum: String,
        verification: String,
    ): ApiData<LoginEntity>?

    suspend fun getStore(
        token: String,
        id: String,
    ): ApiDataList<Store>?
}
