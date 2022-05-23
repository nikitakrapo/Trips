package com.nikitakrapo.trips

import android.app.Application
import android.content.Context
import com.nikitakrapo.trips.analytics.firebase.AnalyticsCommonParameters
import com.nikitakrapo.trips.analytics.firebase.AnalyticsEnvironment
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
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
            AnalyticsCommonParameters(
                environment
            )
        )
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TripsApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }