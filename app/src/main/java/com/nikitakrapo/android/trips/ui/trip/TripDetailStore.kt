package com.nikitakrapo.android.trips.ui.trip

import com.arkivanov.mvikotlin.core.store.Store
import com.nikitakrapo.android.trips.ui.trip.TripDetailStore.*

interface TripDetailStore : Store<Intent, State, Label> {

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

    sealed class Label {
        object CloseScreen : Label()
    }
}