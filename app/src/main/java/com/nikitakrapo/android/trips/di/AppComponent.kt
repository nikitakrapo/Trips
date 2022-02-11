package com.nikitakrapo.android.trips.di

import com.nikitakrapo.android.trips.MainActivity
import dagger.Component

@Component
interface AppComponent {

    fun inject(activity: MainActivity)
}