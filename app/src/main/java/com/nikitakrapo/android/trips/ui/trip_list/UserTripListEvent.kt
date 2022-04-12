package com.nikitakrapo.android.trips.ui.trip_list

sealed class UserTripListEvent {
    class LoadedFromNetwork(
        val tripList: List<Trip>,
    ) : UserTripListEvent()

    class LoadedFromCache(
        val tripList: List<Trip>,
    ) : UserTripListEvent()

    object SwipeRefresh : UserTripListEvent()

    class LongTripClick(
        val trip: Trip
    ) : UserTripListEvent()
}