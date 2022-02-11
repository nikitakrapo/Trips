package com.nikitakrapo.android.trips

import android.app.Application
import android.content.Context
import com.nikitakrapo.android.trips.di.AppComponent
import com.nikitakrapo.android.trips.di.DaggerAppComponent

class TripsApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TripsApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }