package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.remote.api.SplashApi
import com.bvc.data.remote.model.LoginResponse
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
        ): List<LoginResponse>? = safeApiCall(remoteErrorEmitter) { splashApi.getLogin(token).body() }
    }
