package com.bvc.domain.usecase

import com.bvc.domain.repository.PreferenceRepository
import javax.inject.Inject

class PreferenceUseCase
    @Inject
    constructor(
        private val preferencesHelper: PreferenceRepository,
    ) {
        suspend fun setToken(value: String) {
            preferencesHelper.setToken(value)
        }

        suspend fun getToken(): String = preferencesHelper.getToken()

        suspend fun setAffiliteName(value: String) {
            preferencesHelper.setAffiliteName(value)
        }

        suspend fun getAffiliteName(): String = preferencesHelper.getAffiliteName()

        suspend fun setAffiliteType(value: String) {
            preferencesHelper.setAffiliteType(value)
        }

        suspend fun getAffiliteType(): String = preferencesHelper.getAffiliteType()
    }
