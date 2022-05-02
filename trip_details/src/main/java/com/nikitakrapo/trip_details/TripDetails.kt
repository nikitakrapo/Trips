package com.nikitakrapo.trip_details

import kotlinx.coroutines.flow.Flow

interface TripDetails {

    val models: Flow<Model>

    val labels: Flow<Label>

    fun accept(event: Event)

    data class Model(
        val name: String = "",
        val imageUrl: String = "", // TODO: maybe pass smth like bitmap (so that view will be more stupid)
        val description: String? = null,
        val isDescriptionExpanded: Boolean = false
    )

    sealed class Event {
        object ExpandDescriptionClicked : Event()
        object BackArrowClicked : Event()
        object DeleteClicked : Event()
    }

    sealed class Label {
        object CloseScreen : Label()
    }
}