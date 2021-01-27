package com.theathletic.interview.main

import android.app.Application
import com.theathletic.interview.injection.baseModule
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            modules(listOf(baseModule))
        }
    }
}
