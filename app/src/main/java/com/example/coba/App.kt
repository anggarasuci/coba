package com.example.coba

import android.app.Application
import com.example.coba.di.appModules
import com.example.coba.di.domainDataModule
import com.example.coba.di.networkModule
import com.facebook.stetho.Stetho
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Hawk.init(this)
            .build()
        startKoin {
            androidContext(this@App)
            modules(appModules + networkModule + domainDataModule)
        }
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }
}