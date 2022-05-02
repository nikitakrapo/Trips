package com.nikitakrapo.trip_details

import com.arkivanov.mvikotlin.core.store.Store
import com.nikitakrapo.trip_details.TripDetails.Label
import com.nikitakrapo.trip_details.TripDetailsStore.Intent
import com.nikitakrapo.trip_details.TripDetailsStore.State

internal interface TripDetailsStore : Store<Intent, State, Label> {

    sealed class Intent {
        object SwitchDescriptionExpanded : Intent()
        object CloseScreen : Intent()
        object DeleteTrip : Intent()
    }

    data class State(
        val name: String = "",
        val imageUrl: String = "",
        val description: String? = null,
        val isDescriptionExpanded: Boolean = false
    )
}