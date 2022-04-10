package com.nikitakrapo.android.trips.ui.home.trips

sealed class UserTripListEvent {
    class LoadedFromNetwork(
        val tripList: List<Trip>,
    ) : UserTripListEvent()

    object SwipeRefresh : UserTripListEvent()
}