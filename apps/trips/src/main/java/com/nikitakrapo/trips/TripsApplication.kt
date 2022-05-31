package com.nikitakrapo.trips

import android.app.Application
import android.content.Context
import com.nikitakrapo.firebase.FirebaseProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TripsApplication : Application() {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var firebaseProvider: FirebaseProvider

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)

        initFirebase()
    }

    private fun initFirebase() {
        firebaseProvider.init(this)
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TripsApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }