package com.bvc.domain.usecase

import com.bvc.domain.repository.SplashRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class SplashUseCase
    @Inject
    constructor(
        private val splashRepository: SplashRepository,
    ) {
        suspend fun getLogin(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ) = splashRepository.getLogin(remoteErrorEmitter, token)

        suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ) = splashRepository.getAffiliate(remoteErrorEmitter, token)
    }
