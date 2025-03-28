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
    }
