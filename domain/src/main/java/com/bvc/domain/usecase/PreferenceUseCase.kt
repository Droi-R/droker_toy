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

        suspend fun setUserId(value: String) {
            preferencesRepository.setUserId(value)
        }

        suspend fun getUserId(): String = preferencesRepository.getUserId()

        suspend fun setStoreName(value: String) {
            preferencesRepository.setStoreName(value)
        }

        suspend fun getStoreName(): String = preferencesRepository.getStoreName()

        suspend fun setStoreType(value: String) {
            preferencesRepository.setStoreType(value)
        }

        suspend fun getStoreType(): String = preferencesRepository.getStoreType()

        suspend fun setStoreId(value: Int) {
            preferencesRepository.setStoreId(value)
        }

        suspend fun getStoreId(): Int = preferencesRepository.getStoreId()
    }
