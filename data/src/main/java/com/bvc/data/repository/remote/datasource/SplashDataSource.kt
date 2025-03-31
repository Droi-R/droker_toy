package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.LoginResponse
import com.bvc.domain.utils.RemoteErrorEmitter

interface SplashDataSource {
    suspend fun getLogin(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): List<LoginResponse>?
}
