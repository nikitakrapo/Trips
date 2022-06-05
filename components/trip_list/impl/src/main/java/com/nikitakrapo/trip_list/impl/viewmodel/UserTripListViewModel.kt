package com.nikitakrapo.trip_list.impl.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.trip_list.component.TripListFeature
import com.nikitakrapo.trip_list.impl.data.TripListRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserTripListViewModel @Inject constructor(
    tripListRepository: TripListRepositoryImpl,
): ViewModel() {
    val component: TripListFeature = TripListFeature(
        tripListRepository = tripListRepository,
    )

    override fun onCleared() {
        component.dispose()
    }
}