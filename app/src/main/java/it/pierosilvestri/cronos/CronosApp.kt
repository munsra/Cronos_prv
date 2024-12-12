package it.pierosilvestri.cronos

import android.app.Application
import it.pierosilvestri.cronos.di.initKoin
import org.koin.android.ext.koin.androidContext

class CronosApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CronosApp)
        }
    }
}