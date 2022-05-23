package com.nikitakrapo.trip_details.impl.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nikitakrapo.trip_details.TripDetailsFeature
import com.nikitakrapo.trip_details.impl.data.TripDetailsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    tripDetailsRepositoryImpl: TripDetailsRepositoryImpl,
) : ViewModel() {

    //TODO: move it somewhere so that it will always match with MainSections.TripDetails.tripNameArg
    private val tripName = requireNotNull(savedStateHandle.get<String>("tripName"))

    val component = TripDetailsFeature(
        name = tripName,
        tripDetailsRepository = tripDetailsRepositoryImpl
    )

    override fun onCleared() {
        component.dispose()
    }
}