package com.nikitakrapo.trip_details

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.nikitakrapo.data.TripDetailsRepository
import com.nikitakrapo.trip_details.TripDetails.*
import com.nikitakrapo.trip_details.TripDetailsStore.Intent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class TripDetailsComponent(
    storeFactory: StoreFactory,
    componentContext: CoroutineContext,
    tripsRepository: TripDetailsRepository,
    tripName: String,
) : TripDetails {

    private val store = TripDetailsStoreFactory(storeFactory, componentContext, tripsRepository, tripName).create()

    override val models: Flow<Model> = store.states.map(::toModel)

    override val labels: Flow<Label> = store.labels

    override fun accept(event: Event) {
        val intent = when (event) {
            Event.ExpandDescriptionClicked -> Intent.SwitchDescriptionExpanded
            Event.BackArrowClicked -> Intent.CloseScreen
            Event.DeleteClicked -> Intent.DeleteTrip
        }
        store.accept(intent)
    }
}