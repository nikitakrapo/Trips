package com.nikitakrapo.trip_list.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.nikitakrapo.trip_list.component.TripListStore.Intent
import com.nikitakrapo.trip_list.component.TripListStore.State
import com.nikitakrapo.trip_list.data.TripListRepository
import com.nikitakrapo.trip_list.dto.TripModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class TripListStoreProvider(
    private val storeFactory: StoreFactory = DefaultStoreFactory(),
    private val tripListRepository: TripListRepository,
) {

    fun provide(): TripListStore =
        object : TripListStore, Store<Intent, State, Unit> by storeFactory.create(
            name = "TripListStore",
            initialState = State(), //FIXME: not empty
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Unit>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                tripListRepository.getTripListFlow()
                    .map(Message::UpdateTripList)
                    .collect(::dispatch)
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.RefreshTrip -> fetchTrips()
            }
        }

        private fun fetchTrips() { //FIXME: fetch
            dispatch(Message.UpdateIsRefreshing(true))
            scope.launch {
                delay(1000)
                dispatch(Message.UpdateIsRefreshing(false))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.UpdateTripList -> copy(tripList = msg.tripList)
                is Message.UpdateIsRefreshing -> copy(isRefreshing = msg.isRefreshing)
            }
    }

    private sealed class Message {
        class UpdateTripList(val tripList: List<TripModel>) : Message()
        class UpdateIsRefreshing(val isRefreshing: Boolean) : Message()
    }
}