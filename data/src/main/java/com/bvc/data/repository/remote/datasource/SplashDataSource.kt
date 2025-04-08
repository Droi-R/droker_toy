package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.response.AffiliateResponse
import com.bvc.data.remote.model.response.LoginResponse
import com.bvc.data.remote.model.response.ResData
import com.bvc.data.remote.model.response.ResDataList
import com.bvc.domain.utils.RemoteErrorEmitter

interface SplashDataSource {
    suspend fun getLogin(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ResData<LoginResponse>?

    suspend fun getAffiliate(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ResDataList<AffiliateResponse>?
}
