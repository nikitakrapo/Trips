package com.nikitakrapo.trip_list.component

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.nikitakrapo.trip_list.component.TripList.Model
import com.nikitakrapo.trip_list.component.TripList.Output
import com.nikitakrapo.trip_list.component.TripListStore.Intent
import com.nikitakrapo.trip_list.data.TripListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TripListComponent(
    storeFactory: StoreFactory = DefaultStoreFactory(),
    repository: TripListRepository,
    private val output: (Output) -> Unit,
) : TripList {

    private val store = //FIXME: handle its lifecycle by store.dispose
        TripListStoreProvider(
            storeFactory,
            repository
        ).provide()

    override val models: Flow<Model> = store.states.map(::toModel)

    override fun accept(event: TripList.Event): Unit =
        when (event) {
            is TripList.Event.TripClicked -> output(Output.Selected(event.name))
            is TripList.Event.AddTripClicked -> output(Output.AddTripSelected)
            is TripList.Event.SwipeRefreshPulled -> store.accept(Intent.RefreshTrip)
        }
}