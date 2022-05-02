package com.nikitakrapo.android.trips

import android.app.Application
import com.nikitakrapo.analytics.firebase.FirebaseProvider
import com.nikitakrapo.android.trips.data.TripsRepository
import com.nikitakrapo.android.trips.data.trips.TripsModule
import com.nikitakrapo.android.trips.ui.ApplicationModule
import com.nikitakrapo.android.trips.ui.MainActivity
import com.nikitakrapo.android.trips.viewmodels.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    ApplicationModule::class,
    TripsModule::class,
    ViewModelModule::class
])
@Singleton
interface AppComponent {

    fun firebaseProvider(): FirebaseProvider

    fun tripsRespository(): TripsRepository

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}