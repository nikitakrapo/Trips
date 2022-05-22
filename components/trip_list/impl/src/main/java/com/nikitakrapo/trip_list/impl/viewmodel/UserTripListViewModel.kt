package com.nikitakrapo.trip_list.impl.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.trip_list.component.TripList
import com.nikitakrapo.trip_list.impl.data.TripListRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserTripListViewModel @Inject constructor(
    tripListRepository: TripListRepositoryImpl,
): ViewModel() {
    val component: TripList = TripList(
        tripListRepository = tripListRepository,
    )

    override fun onCleared() {
        component.dispose()
    }
}