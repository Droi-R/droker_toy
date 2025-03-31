package com.bvc.domain.repository

import com.bvc.domain.model.LoginEntity
import com.bvc.domain.utils.RemoteErrorEmitter

interface SplashRepository {
    suspend fun getLogin(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): List<LoginEntity>?
}
