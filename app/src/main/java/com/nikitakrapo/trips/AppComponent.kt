package com.nikitakrapo.trips

import android.app.Application
import com.nikitakrapo.trips.analytics.firebase.FirebaseProvider
import com.nikitakrapo.trips.data.TripsRepository
import com.nikitakrapo.trips.data.cache.TripsModule
import com.nikitakrapo.trips.activity.MainActivity
import com.nikitakrapo.trips.components.add_trip.AddTripRepository
import com.nikitakrapo.trips.components.trip_details.TripDetailsRepository
import com.nikitakrapo.trips.viewmodels.ViewModelModule
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

    fun tripDetailsRepository(): TripDetailsRepository

    fun addTripRepository(): AddTripRepository

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}