package com.bvc.data.repository

import com.bvc.data.mapper.Mapper
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.repository.MainRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainRepositoryImpl
    @Inject
    constructor(
        private val mainDataSource: MainDataSource,
    ) : MainRepository {
        override suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): List<AffiliateEntity>? = Mapper.mapperAffiliate(mainDataSource.getAffiliate(remoteErrorEmitter, token))
    }
