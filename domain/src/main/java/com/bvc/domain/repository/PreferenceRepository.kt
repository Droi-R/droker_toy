package com.bvc.domain.repository

interface PreferenceRepository {
    suspend fun setToken(value: String)

    suspend fun getToken(): String

    suspend fun setRefreshToken(value: String)

    suspend fun getRefreshToken(): String

    suspend fun setAffiliteName(value: String)

    suspend fun getAffiliteName(): String

    suspend fun setAffiliteType(value: String)

    suspend fun getAffiliteType(): String

    suspend fun setAffiliteId(value: String)

    suspend fun getAffiliteId(): String
}
