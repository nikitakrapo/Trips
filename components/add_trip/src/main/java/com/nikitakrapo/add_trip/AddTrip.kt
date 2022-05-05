package com.nikitakrapo.add_trip

import kotlinx.coroutines.flow.Flow

interface AddTrip {

    val models: Flow<Model>

    val labels: Flow<Label>

    fun accept(event: Event)

    data class Model(
        val nameText: String = "",
        val nameError: TripNameError? = null, //TODO: make this more abstract - smth like textFieldError
        val isAddButtonEnabled: Boolean = false,
        val isAdding: Boolean = false,
    )

    sealed class TripNameError {
        class InvalidCharacters(val characters: Set<Char>) : TripNameError()
        class TooShort(val minChars: Int) : TripNameError()
        class TooLong(val maxChars: Int) : TripNameError()
    }

    sealed class Event {
        class NameTextFieldChanged(val text: String) : Event()
        object AddTripClicked : Event()
        object BackArrowClicked : Event()
    }

    sealed class Label {
        object CloseScreen : Label()
    }
}