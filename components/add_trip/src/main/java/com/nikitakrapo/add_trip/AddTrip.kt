package com.nikitakrapo.add_trip

import kotlinx.coroutines.flow.Flow

interface AddTrip {

    val models: Flow<Model>

    val labels: Flow<Label>

    fun accept(event: Event)

    data class Model(
        val nameText: String = "",
        val isAddButtonLoading: Boolean = false,
    )

    sealed class Event {
        class NameTextFieldChanged(val text: String) : Event()
        object AddTripClicked : Event()
        object BackArrowClicked : Event()
    }

    sealed class Label {
        object CloseScreen : Label()
    }
}