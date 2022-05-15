package com.nikitakrapo.trips.components.trip_list

import com.nikitakrapo.trips.data.dto.Trip

data class UserTripListUiState(
    val showProgressBar: Boolean = true,
    val swipeRefreshRefreshing: Boolean = false,
    val tripList: List<Trip> = emptyList(),
) {
    val hasPlannedTrips: Boolean
        get() = tripList.isNotEmpty()
}