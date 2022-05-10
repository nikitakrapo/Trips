package com.nikitakrapo.trip_details

import com.arkivanov.mvikotlin.core.store.Store
import com.nikitakrapo.trip_details.TripDetails.Label
import com.nikitakrapo.trip_details.TripDetailsStore.Intent
import com.nikitakrapo.trip_details.TripDetailsStore.State

internal interface TripDetailsStore : Store<Intent, State, Label> {

    sealed class Intent {
        object CloseScreen : Intent()
        object DeleteTrip : Intent()
        object OpenDropdownMenu : Intent()
        object CloseDropdownMenu : Intent()
    }

    data class State(
        val name: String = "",
        val isDropdownMenuOpened: Boolean = false,
    )
}