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
//        startKoin {
//            androidContext(this@App)
//            modules(appModule)
//        }
    }

    companion object {
        private lateinit var instance: App
        var dialog: Dialog? = null
        lateinit var gMapTmpChunk: HashMap<String, Any>
        fun getInstance(): App = instance

//        lateinit var cookieJar: CookieJar
        fun disProgress() {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    try {
                        if (dialog != null) {
                            if (dialog!!.isShowing) {
                                dialog!!.dismiss()
                                dialog = null
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                0,
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
