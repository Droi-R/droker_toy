package com.bvc.ordering

import android.app.Application
import android.app.Dialog
import android.os.Handler
import android.os.Looper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        gMapTmpChunk = HashMap()
    }

    companion object {
        private lateinit var instance: App
        lateinit var gMapTmpChunk: HashMap<String, Any>
        fun getInstance(): App = instance

    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
