package com.nikitakrapo.trip_list.component

import com.nikitakrapo.trip_list.component.TripList.Model
import com.nikitakrapo.trip_list.component.TripListStore.State

internal fun toModel(state: State): Model =
    Model(
        tripList = state.tripList,
        isRefreshing = state.isRefreshing
    )