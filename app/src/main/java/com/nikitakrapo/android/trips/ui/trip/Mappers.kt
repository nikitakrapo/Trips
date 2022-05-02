package com.nikitakrapo.android.trips.ui.trip

fun toModel(state: TripDetailStore.State): TripDetail.Model {
    return TripDetail.Model(
        name = state.name,
        imageUrl = state.imageUrl,
        description = state.description,
        isDescriptionExpanded = state.isDescriptionExpanded
    )
}