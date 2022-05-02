package com.nikitakrapo.trip_details

internal fun toModel(state: TripDetailsStore.State): TripDetails.Model {
    return TripDetails.Model(
        name = state.name,
        imageUrl = state.imageUrl,
        description = state.description,
        isDescriptionExpanded = state.isDescriptionExpanded
    )
}