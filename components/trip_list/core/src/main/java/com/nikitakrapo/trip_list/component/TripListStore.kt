package com.nikitakrapo.trip_list.component

import com.arkivanov.mvikotlin.core.store.Store
import com.nikitakrapo.trip_list.component.TripListStore.Intent
import com.nikitakrapo.trip_list.component.TripListStore.State
import com.nikitakrapo.trip_list.dto.TripModel

internal interface TripListStore : Store<Intent, State, Unit> {

    sealed class Intent {
        object RefreshTrip : Intent()
    }

    data class State(
        val tripList: List<TripModel> = emptyList(),
        val isRefreshing: Boolean = false,
    )
}