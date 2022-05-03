package com.nikitakrapo.add_trip

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.nikitakrapo.add_trip.AddTrip.Label
import com.nikitakrapo.add_trip.AddTripStore.Intent
import com.nikitakrapo.add_trip.AddTripStore.State
import com.nikitakrapo.data.AddTripRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class AddTripStoreFactory(
    private val storeFactory: StoreFactory,
    private val mainContext: CoroutineContext,
    private val addTripRepository: AddTripRepository,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AddTripStore =
        object : AddTripStore, Store<Intent, State, Label> by storeFactory.create<Intent, Unit, Message, State, Label>(
            name = "AddTripFactory",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = coroutineExecutorFactory(mainContext) {
                onIntent<Intent.AddTrip> {
                    launch(Dispatchers.IO) { //TODO: move dispatchers to di
                        addTripRepository.addTrip(it.name)
                        withContext(Dispatchers.Main) {
                            dispatch(Message.AddedTrip)
                            publish(Label.CloseScreen)
                        }
                    }
                    dispatch(Message.AddingStarted)
                }

                onIntent<Intent.ChangeNameText> {
                    dispatch(Message.UpdateNameText(it.text))
                }

                onIntent<Intent.CloseScreen> {
                    publish(Label.CloseScreen)
                }

                onIntent<Intent.GoBack> {
                    publish(Label.CloseScreen) //TODO: think about it
                }
            },
            reducer = { message ->
                when (message) {
                    is Message.UpdateNameText -> copy(nameText = message.text)
                    is Message.AddingStarted -> copy(isAddButtonLoading = true)
                    is Message.AddedTrip -> copy(/* TODO: handle onAdd logic  */)
                }
            }
        ) {}

    private sealed class Message {
        class UpdateNameText(val text: String) : Message()
        object AddingStarted : Message()
        object AddedTrip : Message()
    }
}