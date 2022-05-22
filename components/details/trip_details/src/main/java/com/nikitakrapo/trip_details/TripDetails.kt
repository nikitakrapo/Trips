package com.nikitakrapo.trip_details

import kotlinx.coroutines.flow.Flow

interface TripDetails {

    val models: Flow<Model>

    val labels: Flow<Label>

    fun accept(event: Event)

    data class Model(
        val name: String = "",
        val isDropdownMenuOpened: Boolean = false,
    )

    sealed class Event {
        object BackArrowClicked : Event()
        object MoreClicked : Event()
        object OutsideOfDropdownClicked : Event()
        object DeleteClicked : Event()
    }

    sealed class Label {
        object CloseScreen : Label()
    }

    interface ViewCallbacks {
        fun closeScreen()
    }
}