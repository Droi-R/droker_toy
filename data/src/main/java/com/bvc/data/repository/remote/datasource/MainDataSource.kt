package com.bvc.data.repository.remote.datasource

import com.bvc.data.remote.model.AffiliateResponse
import com.bvc.data.remote.model.ResDataList
import com.bvc.domain.utils.RemoteErrorEmitter

interface MainDataSource {
    suspend fun getAffiliate(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): ResDataList<AffiliateResponse>?
}
