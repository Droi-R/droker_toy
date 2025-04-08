package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.remote.api.SplashApi
import com.bvc.data.remote.model.response.AffiliateResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.data.repository.remote.datasource.SplashDataSource
import com.bvc.data.utils.base.BaseRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class SplashDataSourceImpl
    @Inject
    constructor(
        private val splashApi: SplashApi,
    ) : BaseRepository(),
        SplashDataSource {
        override suspend fun getLogin(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ResData<LoginResponse>? = safeApiCall(remoteErrorEmitter) { splashApi.getLogin(token).body() }

        override suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ResDataList<AffiliateResponse>? = safeApiCall(remoteErrorEmitter) { splashApi.getAffiliate(token).body() }
    }
