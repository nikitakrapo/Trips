package com.nikitakrapo.trips

import android.app.Application
import android.content.Context
import com.nikitakrapo.analytics.firebase.AnalyticsCommonParameters
import com.nikitakrapo.analytics.firebase.AnalyticsEnvironment
import com.nikitakrapo.analytics.firebase.FirebaseWrapper
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
        val commonParameters = AnalyticsCommonParameters(
            environment = if (BuildConfig.DEBUG) {
                AnalyticsEnvironment.TESTING
            } else {
                AnalyticsEnvironment.PRODUCTION
            }
        )
        FirebaseWrapper.init(this, commonParameters)
    }

}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TripsApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }