package com.nikitakrapo.android.trips.ui.add_trip

sealed class AddTripEvent {
    class NameTextFieldChanged(val text: String) : AddTripEvent()
}
