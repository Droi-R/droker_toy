package com.bvc.data.repository

import com.bvc.data.mapper.ResponseMapper
import com.bvc.data.repository.remote.datasource.SplashDataSource
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.EmptyEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.Store
import com.bvc.domain.repository.SplashRepository
import javax.inject.Inject

class SplashRepositoryImpl
    @Inject
    constructor(
        private val splashDataSource: SplashDataSource,
    ) : SplashRepository {
        override suspend fun sendSms(phoneNum: String): ApiData<EmptyEntity> = ResponseMapper.mapSendSms(splashDataSource.sendSms(phoneNum))

        override suspend fun verifySms(
            phoneNum: String,
            verification: String,
        ): ApiData<EmptyEntity> = ResponseMapper.mapVerifySms(splashDataSource.verifySms(phoneNum, verification))

        override suspend fun signUp(
            phoneNum: String,
            verification: String,
        ): ApiData<LoginEntity> = ResponseMapper.mapSignUp(splashDataSource.signUp(phoneNum, verification))

        override suspend fun getLogin(
            phoneNum: String,
            verification: String,
        ): ApiData<LoginEntity> = ResponseMapper.mapLogin(splashDataSource.getLogin(phoneNum, verification))

        override suspend fun getStore(
            token: String,
            id: String,
        ): ApiDataList<Store> = ResponseMapper.mapStores(splashDataSource.getStore(token, id))
    }
