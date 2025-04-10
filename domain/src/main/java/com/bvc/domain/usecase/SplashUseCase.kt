package com.bvc.domain.usecase

import com.bvc.domain.repository.SplashRepository
import com.bvc.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class SplashUseCase
    @Inject
    constructor(
        private val splashRepository: SplashRepository,
    ) {
        suspend fun sendSms(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
        ) = splashRepository.sendSms(remoteErrorEmitter, phoneNum)

        suspend fun verifySms(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ) = splashRepository.verifySms(remoteErrorEmitter, phoneNum, verification)

        suspend fun signUp(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ) = splashRepository.signUp(remoteErrorEmitter, phoneNum, verification)

        suspend fun getLogin(
            remoteErrorEmitter: RemoteErrorEmitter,
            phoneNum: String,
            verification: String,
        ) = splashRepository.getLogin(remoteErrorEmitter, phoneNum, verification)

        suspend fun getAffiliate(
            remoteErrorEmitter: RemoteErrorEmitter,
            token: String,
        ) = splashRepository.getAffiliate(remoteErrorEmitter, token)
    }
