package com.theathletic.interview.main

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.theathletic.interview.injection.baseModule
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.core.context.startKoin
import timber.log.Timber
import java.io.File

class MainApplication : Application() {

    companion object {
        lateinit var picassoWithCache: Picasso
    }

    override fun onCreate() {
        super.onCreate()
        initPicasso()

        Timber.plant(Timber.DebugTree())
        startKoin {
            modules(listOf(baseModule))
        }
    }

    private fun initPicasso() {
        val httpCacheDirectory = File(cacheDir, "picasso-cache")
        val cache = Cache(httpCacheDirectory, 15 * 1024 * 1024)

        val okHttpClientBuilder =
            OkHttpClient.Builder().cache(cache)

        picassoWithCache =
            Picasso.Builder(this).downloader(OkHttp3Downloader(okHttpClientBuilder.build())).build()

    }
}
