package com.bvc.domain.repository

import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.utils.RemoteErrorEmitter

interface MainRepository {
    suspend fun getAffiliate(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): List<AffiliateEntity>?
}
