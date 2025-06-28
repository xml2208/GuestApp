package com.example.guestapp

import android.app.Application
import com.example.guestapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class GuestApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GuestApp)
            modules(appModule)
        }

    }
}