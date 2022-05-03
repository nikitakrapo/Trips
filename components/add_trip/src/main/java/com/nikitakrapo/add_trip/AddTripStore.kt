package com.nikitakrapo.add_trip

import com.arkivanov.mvikotlin.core.store.Store
import com.nikitakrapo.add_trip.AddTrip.Label
import com.nikitakrapo.add_trip.AddTripStore.Intent
import com.nikitakrapo.add_trip.AddTripStore.State

internal interface AddTripStore : Store<Intent, State, Label> {

    sealed class Intent {
        class ChangeNameText(val text: String) : Intent()
        class AddTrip(val name: String) : Intent()
        object CloseScreen : Intent()
        object GoBack : Intent()
    }

    data class State(
        val nameText: String = "",
        val isAddButtonLoading: Boolean = false,
    )
}