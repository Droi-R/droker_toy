package com.bvc.domain.repository

interface PreferenceRepository {
    suspend fun setToken(value: String)

    suspend fun getToken(): String
}
