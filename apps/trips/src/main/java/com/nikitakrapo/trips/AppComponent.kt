package com.nikitakrapo.trips

import android.app.Application
import com.nikitakrapo.trips.data.cache.TripsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    ApplicationModule::class,
    TripsModule::class, //TODO: fix scope
])
@Singleton
interface AppComponent {

    fun inject(application: TripsApplication)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}