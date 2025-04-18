package com.bvc.domain.repository

interface PreferenceRepository {
    suspend fun setToken(value: String)

    suspend fun getToken(): String

    suspend fun setRefreshToken(value: String)

    suspend fun getRefreshToken(): String

    suspend fun setUserId(value: String)

    suspend fun getUserId(): String

    suspend fun setStoreName(value: String)

    suspend fun getStoreName(): String

    suspend fun setStoreType(value: String)

    suspend fun getStoreType(): String

    suspend fun setStoreId(value: Int)

    suspend fun getStoreId(): Int
}
