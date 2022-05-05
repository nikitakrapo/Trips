package com.nikitakrapo.add_trip

import com.nikitakrapo.add_trip.AddTrip.Model
import com.nikitakrapo.add_trip.AddTripStore.State

internal fun toModel(state: State): Model {
    return Model(
        nameText = state.nameText,
        nameError = state.nameError,
        isAddButtonEnabled = state.isAddButtonEnabled,
        isAdding = state.isAdding,
    )
}