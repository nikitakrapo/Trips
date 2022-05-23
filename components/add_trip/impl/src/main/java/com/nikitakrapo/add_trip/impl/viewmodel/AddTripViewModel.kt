package com.nikitakrapo.add_trip.impl.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitakrapo.add_trip.AddTripFeature
import com.nikitakrapo.add_trip.impl.data.AddTripRepositoryImpl
import com.nikitakrapo.add_trip.impl.data.TripNameTextValidatorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTripViewModel @Inject constructor(
    tripListRepository: AddTripRepositoryImpl,
    tripNameTextValidatorImpl: TripNameTextValidatorImpl,
): ViewModel() {
    val component: AddTripFeature = AddTripFeature(
        addTripRepository = tripListRepository,
        tripNameTextValidator = tripNameTextValidatorImpl,
    )

    override fun onCleared() {
        component.dispose()
    }
}