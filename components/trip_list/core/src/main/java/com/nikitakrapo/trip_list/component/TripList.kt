package com.nikitakrapo.trip_list.component

import com.nikitakrapo.trip_list.dto.TripModel
import kotlinx.coroutines.flow.Flow

interface TripList {
    val models: Flow<Model>

    fun accept(event: Event)

    data class Model(
        val tripList: List<TripModel> = emptyList(),
        val isRefreshing: Boolean = false,
    )

    sealed class Event {
        class TripClicked(val name: String) : Event()
        object AddTripClicked : Event()
        object SwipeRefreshPulled : Event()
    }

    sealed class Output {
        data class Selected(val name: String) : Output()
        object AddTripSelected : Output()
    }
}