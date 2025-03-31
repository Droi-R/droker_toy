package com.bvc.data.repository

import com.bvc.data.mapper.Mapper
import com.bvc.data.repository.remote.datasource.SplashDataSource
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.repository.SplashRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class SplashRepositoryImpl
    @Inject
    constructor(
        private val splashDataSource: SplashDataSource,
    ) : SplashRepository {
        override suspend fun getLogin(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): List<LoginEntity>? = Mapper.mapperLogin(splashDataSource.getLogin(remoteErrorEmitter, token))
    }
