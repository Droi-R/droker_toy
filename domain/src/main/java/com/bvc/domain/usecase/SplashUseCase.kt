package com.bvc.domain.usecase

import com.bvc.domain.repository.SplashRepository
import javax.inject.Inject

class SplashUseCase
    @Inject
    constructor(
        private val splashRepository: SplashRepository,
    ) {
        suspend fun sendSms(phoneNum: String) = splashRepository.sendSms(phoneNum)

        suspend fun verifySms(
            phoneNum: String,
            verification: String,
        ) = splashRepository.verifySms(phoneNum, verification)

        suspend fun signUp(
            phoneNum: String,
            verification: String,
        ) = splashRepository.signUp(phoneNum, verification)

        suspend fun getLogin(
            phoneNum: String,
            verification: String,
        ) = splashRepository.getLogin(phoneNum, verification)

        suspend fun getStore(
            token: String,
            id: String,
        ) = splashRepository.getStore(token, id)
    }
