package com.nikitakrapo.trip_details

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.nikitakrapo.data.TripDetailsRepository
import com.nikitakrapo.trip_details.TripDetails.Label
import com.nikitakrapo.trip_details.TripDetailsStore.Intent
import com.nikitakrapo.trip_details.TripDetailsStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class TripDetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val tripsRepository: TripDetailsRepository,
    private val tripName: String
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): TripDetailsStore =
        // TODO: E/MVIKotlin: Could not enable time travel for the store: TripDetailStore. Duplicate store name.
        object : TripDetailsStore, Store<Intent, State, Label> by storeFactory.create<Intent, Unit, Msg, State, Label>(
            name = "TripDetailStore",
            initialState = State(name = tripName),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = coroutineExecutorFactory(mainContext) {
                onAction<Unit> {
                    launch {
                        //FIXME: fetch from db
                        val description = "Stub description"
                        dispatch(Msg.Loaded(description))
                    }
                }

                onIntent<Intent.SwitchDescriptionExpanded> {
                    dispatch(Msg.SwitchDescriptionExpanded)
                }

                onIntent<Intent.CloseScreen> {
                    publish(Label.CloseScreen)
                }

                onIntent<Intent.DeleteTrip> {
                    launch(Dispatchers.IO) { //TODO: add di for dispatchers
                        tripsRepository.removeTrip(tripName)
                    }
                    publish(Label.CloseScreen)
                }
            },
            reducer = { msg ->
                when (msg) {
                    is Msg.SwitchDescriptionExpanded -> copy(isDescriptionExpanded = !isDescriptionExpanded)
                    is Msg.Loaded -> copy(description = msg.description)
                }
            },
        ) {}

    private sealed class Msg {
        object SwitchDescriptionExpanded : Msg()
        data class Loaded(val description: String) : Msg()
    }
}