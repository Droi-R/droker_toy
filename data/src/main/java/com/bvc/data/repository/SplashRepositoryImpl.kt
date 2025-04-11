package com.bvc.data.repository

import com.bvc.data.mapper.ResponseMapper
import com.bvc.data.repository.remote.datasource.SplashDataSource
import com.bvc.domain.model.ApiData
import com.bvc.domain.model.ApiDataList
import com.bvc.domain.model.EmptyEntity
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.model.Store
import com.bvc.domain.repository.SplashRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class SplashRepositoryImpl
    @Inject
    constructor(
        private val splashDataSource: SplashDataSource,
    ) : SplashRepository {
        override suspend fun sendSms(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
        ): ApiData<EmptyEntity> = ResponseMapper.mapSendSms(splashDataSource.sendSms(remoteErrorEmitter, phoneNum))

        override suspend fun verifySms(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ): ApiData<EmptyEntity> = ResponseMapper.mapVerifySms(splashDataSource.verifySms(remoteErrorEmitter, phoneNum, verification))

        override suspend fun signUp(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ): ApiData<LoginEntity> = ResponseMapper.mapSignUp(splashDataSource.signUp(remoteErrorEmitter, phoneNum, verification))

        override suspend fun getLogin(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ): ApiData<LoginEntity> = ResponseMapper.mapLogin(splashDataSource.getLogin(remoteErrorEmitter, phoneNum, verification))

        override suspend fun getStore(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
            id: String,
        ): ApiDataList<Store> = ResponseMapper.mapStore(splashDataSource.getStore(remoteErrorEmitter, token, id))
    }
