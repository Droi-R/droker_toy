package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.LoginResponse
import com.bvc.data.remote.model.ResData
import com.bvc.data.remote.model.ResDataList
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
