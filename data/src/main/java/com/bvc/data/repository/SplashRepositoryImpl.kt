package com.bvc.data.repository

import com.bvc.data.mapper.ResponseMapper
import com.bvc.data.repository.remote.datasource.SplashDataSource
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.Data
import com.bvc.domain.model.DataList
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
        ): Data<LoginEntity> = ResponseMapper.mapLogin(splashDataSource.getLogin(remoteErrorEmitter, token))

        override suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): DataList<AffiliateEntity> = ResponseMapper.mapAffiliate(splashDataSource.getAffiliate(remoteErrorEmitter, token))
    }
