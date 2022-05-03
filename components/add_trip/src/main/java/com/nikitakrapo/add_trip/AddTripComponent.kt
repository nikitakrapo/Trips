package com.nikitakrapo.add_trip

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.nikitakrapo.add_trip.AddTrip.*
import com.nikitakrapo.add_trip.AddTripStore.Intent
import com.nikitakrapo.data.AddTripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class AddTripComponent(
    storeFactory: StoreFactory,
    componentContext: CoroutineContext,
    addTripRepository: AddTripRepository,
) : AddTrip {

    private val store = AddTripStoreFactory(storeFactory, componentContext, addTripRepository).create()

    override val models: Flow<Model> = store.states.map(::toModel)

    override val labels: Flow<Label> = store.labels

    override fun accept(event: Event) {
        val intent = when (event) {
            is Event.AddTripClicked -> Intent.AddTrip(store.state.nameText)
            is Event.NameTextFieldChanged -> Intent.ChangeNameText(event.text)
            is Event.BackArrowClicked -> Intent.GoBack
        }
        store.accept(intent)
    }
}