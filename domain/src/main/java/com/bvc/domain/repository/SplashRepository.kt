package com.bvc.domain.repository

import com.bvc.domain.model.AffiliateEntity
import com.bvc.domain.model.Data
import com.bvc.domain.model.DataList
import com.bvc.domain.model.LoginEntity
import com.bvc.domain.utils.RemoteErrorEmitter

interface SplashRepository {
    suspend fun getLogin(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): Data<LoginEntity>?

    suspend fun getAffiliate(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
    ): DataList<AffiliateEntity>?
}
