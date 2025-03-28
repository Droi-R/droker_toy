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
    }
