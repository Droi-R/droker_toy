package com.bvc.domain.usecase

import com.bvc.domain.repository.PreferenceRepository
import javax.inject.Inject

class PreferenceUseCase
    @Inject
    constructor(
        private val preferencesRepository: PreferenceRepository,
    ) {
        suspend fun setToken(value: String) {
            preferencesRepository.setToken(value)
        }

        suspend fun getToken(): String = preferencesRepository.getToken()

        suspend fun setRefreshToken(value: String) {
            preferencesRepository.setRefreshToken(value)
        }

        suspend fun getRefreshToken(): String = preferencesRepository.getRefreshToken()

        suspend fun setAffiliteName(value: String) {
            preferencesRepository.setAffiliteName(value)
        }

        suspend fun getAffiliteName(): String = preferencesRepository.getAffiliteName()

        suspend fun setAffiliteType(value: String) {
            preferencesRepository.setAffiliteType(value)
        }

        suspend fun getAffiliteType(): String = preferencesRepository.getAffiliteType()

        suspend fun setAffiliteId(value: String) {
            preferencesRepository.setAffiliteId(value)
        }

        suspend fun getAffiliteId(): String = preferencesRepository.getAffiliteId()
    }
