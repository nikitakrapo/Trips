package com.nikitakrapo.android.trips.ui.home.sections.trips.add_trip

sealed class AddTripEvent {
    class NameTextFieldChanged(val text: String) : AddTripEvent()
}
