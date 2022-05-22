package com.nikitakrapo.impl.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.impl.data.TripListRepositoryImpl
import com.nikitakrapo.trip_list.component.TripList
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