package com.nikitakrapo.android.trips

import com.nikitakrapo.android.trips.ui.MainActivity
import dagger.Component

@Component
interface AppComponent {

    fun inject(activity: MainActivity)
}