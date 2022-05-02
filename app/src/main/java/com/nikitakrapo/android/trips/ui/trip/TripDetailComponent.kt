package com.nikitakrapo.android.trips.ui.trip

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.nikitakrapo.android.trips.data.TripsRepository
import com.nikitakrapo.android.trips.ui.trip.TripDetail.Event
import com.nikitakrapo.android.trips.ui.trip.TripDetail.Model
import com.nikitakrapo.android.trips.ui.trip.TripDetailStore.Intent
import com.nikitakrapo.android.trips.ui.trip.TripDetailStore.Label
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class TripDetailComponent(
    storeFactory: StoreFactory,
    componentContext: CoroutineContext,
    tripsRepository: TripsRepository,
    tripName: String,
) : TripDetail {

    private val store = TripDetailStoreFactory(storeFactory, componentContext, tripsRepository, tripName).create()

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