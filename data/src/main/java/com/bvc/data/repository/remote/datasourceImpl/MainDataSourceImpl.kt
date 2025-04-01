package com.bvc.data.repository.remote.datasourceImpl

import com.bvc.data.remote.api.MainApi
import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.ResDataList
import com.bvc.data.repository.remote.datasource.MainDataSource
import com.bvc.data.utils.base.BaseRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MainDataSourceImpl
    @Inject
    constructor(
        private val mainApi: MainApi,
    ) : BaseRepository(),
        MainDataSource {
        override suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ): ResDataList<AffiliateResponse>? = safeApiCall(remoteErrorEmitter) { mainApi.getAffiliate(token).body() }
    }
