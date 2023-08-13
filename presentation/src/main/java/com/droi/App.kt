package com.droi

import android.app.Application
import android.app.Dialog
import android.os.Handler
import android.os.Looper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        gMapTmpChunk = HashMap()

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    companion object {
        @JvmField
        var isQr: Boolean = false
        var move_tap: String = ""
        var instance: com.droi.App? = null
        var dialog: Dialog? = null
        lateinit var gMapTmpChunk: HashMap<String, Any>

//        lateinit var cookieJar: CookieJar
        fun disProgress() {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    try {
                        if (com.droi.App.Companion.dialog != null) {
                            if (com.droi.App.Companion.dialog!!.isShowing) {
                                com.droi.App.Companion.dialog!!.dismiss()
                                com.droi.App.Companion.dialog = null
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },0,)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        com.droi.App.Companion.instance = null
    }

    fun getGlobalApplicationContext(): com.droi.App {
        checkNotNull(com.droi.App.Companion.instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return com.droi.App.Companion.instance!!
    }
}
