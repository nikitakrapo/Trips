package com.nikitakrapo.android.trips.ui.home.trips

data class UserTripListUiState(
    val showProgressBar: Boolean = true,
    val swipeRefreshRefreshing: Boolean = false,
    val tripList: List<Trip> = emptyList(),
) {
    val hasPlannedTrips: Boolean
        get() = tripList.isNotEmpty()
}