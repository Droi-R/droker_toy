package com.bvc.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.bvc.domain.repository.PreferenceRepository
import com.bvc.domain.utils.Constant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl
    @Inject
    constructor(
        context: Context,
    ) : PreferenceRepository {
        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        override suspend fun setToken(value: String) {
            sharedPreferences.edit { putString(Constant.PREF.BVC_TOKEN, value) }
        }

        override suspend fun getToken(): String = sharedPreferences.getString(Constant.PREF.BVC_TOKEN, "") ?: ""

        override suspend fun setRefreshToken(value: String) {
            sharedPreferences.edit { putString(Constant.PREF.BVC_REFRESH_TOKEN, value) }
        }

        override suspend fun getRefreshToken(): String = sharedPreferences.getString(Constant.PREF.BVC_REFRESH_TOKEN, "") ?: ""

        override suspend fun setUserId(value: String) {
            sharedPreferences.edit { putString(Constant.PREF.BVC_USER_ID, value) }
        }

        override suspend fun getUserId(): String = sharedPreferences.getString(Constant.PREF.BVC_USER_ID, "") ?: ""

        override suspend fun setStoreName(value: String) {
            sharedPreferences.edit { putString(Constant.PREF.BVC_STORE_NAME, value) }
        }

        override suspend fun getStoreName(): String = sharedPreferences.getString(Constant.PREF.BVC_STORE_NAME, "") ?: ""

        override suspend fun setStoreType(value: String) {
            sharedPreferences.edit { putString(Constant.PREF.BVC_STORE_TYPE, value) }
        }

        override suspend fun getStoreType(): String = sharedPreferences.getString(Constant.PREF.BVC_STORE_TYPE, "") ?: ""

        override suspend fun setStoreId(value: Int) {
            sharedPreferences.edit { putInt(Constant.PREF.BVC_STORE_ID, value) }
        }

        override suspend fun getStoreId(): Int = sharedPreferences.getInt(Constant.PREF.BVC_STORE_ID, -1)
    }
