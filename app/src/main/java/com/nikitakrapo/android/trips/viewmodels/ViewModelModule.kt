package com.nikitakrapo.android.trips.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitakrapo.android.trips.ui.add_trip.AddTripViewModel
import com.nikitakrapo.android.trips.ui.trip_list.UserTripListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserTripListViewModel::class)
    abstract fun userTripListViewModel(viewModel: UserTripListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTripViewModel::class)
    abstract fun addTripViewModel(viewModel: AddTripViewModel): ViewModel
}