package com.peakscircle.legalinfodemo

import android.app.Application
import android.content.Context
import com.peakscircle.legalinfodemo.di.appModule
import com.peakscircle.legalinfodemo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun attachBaseContext(base: Context) {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }

        super.attachBaseContext(base)
    }

}