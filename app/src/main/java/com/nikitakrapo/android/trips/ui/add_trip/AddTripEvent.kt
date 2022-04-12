package com.nikitakrapo.android.trips.ui.add_trip

sealed class AddTripEvent {
    // UI events
    object BackArrowClicked : AddTripEvent()
    class NameTextFieldChanged(val text: String) : AddTripEvent()
    object AddTripClicked : AddTripEvent()

    // Middleware events
    object TripAdded : AddTripEvent()
}