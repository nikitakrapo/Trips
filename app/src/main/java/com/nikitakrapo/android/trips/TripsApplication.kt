package com.nikitakrapo.android.trips

import android.app.Application
import android.content.Context
import com.nikitakrapo.analytics.firebase.AnalyticsDefaultParameters
import com.nikitakrapo.analytics.firebase.AnalyticsEnvironment
import timber.log.Timber

class TripsApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initFirebase()
    }

    private fun initFirebase() {
        val environment = if (BuildConfig.DEBUG) {
            AnalyticsEnvironment.TESTING
        } else {
            AnalyticsEnvironment.PRODUCTION
        }
        val firebaseProvider = appComponent.firebaseProvider()
        firebaseProvider.init(
            this,
            AnalyticsDefaultParameters(
                environment,
                BuildConfig.VERSION_NAME
            )
        )
        firebaseProvider.reportAppOpen()
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TripsApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }