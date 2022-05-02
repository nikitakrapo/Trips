package com.nikitakrapo.android.trips.ui

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
interface ApplicationModule {

    @Binds
    fun applicationContext(application: Application): Context
}