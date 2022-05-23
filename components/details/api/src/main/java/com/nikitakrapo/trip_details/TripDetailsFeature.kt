package com.nikitakrapo.trip_details

import com.nikitakrapo.data.TripDetailsRepository
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import com.nikitakrapo.trip_details.TripDetailsFeature.Effect
import com.nikitakrapo.trip_details.TripDetailsFeature.Intent
import com.nikitakrapo.trip_details.TripDetailsFeature.News
import com.nikitakrapo.trip_details.TripDetailsFeature.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TripDetailsFeature(
    name: String,
    tripDetailsRepository: TripDetailsRepository,
) : BaseFeature<Intent, Intent, Effect, State, News>(
    initialState = State(name),
    intentToAction = { it },
    actor = ActorImpl(tripDetailsRepository),
    reducer = ReducerImpl,
    newsPublisher = NewsPublisherImpl,
) {

    sealed class Intent {
        object CloseScreen : Intent()
        object DeleteTrip : Intent()
        object OpenDropdownMenu : Intent()
        object CloseDropdownMenu : Intent()
    }

    sealed class Effect {
        object OpenDropdownMenu : Effect()
        object CloseDropdownMenu : Effect()
        object CloseScreen : Effect()
    }

    data class State(
        val name: String = "",
        val isDropdownMenuOpened: Boolean = false,
    )

    sealed class News {
        object CloseScreen : News()
    }

    class ActorImpl(
        private val tripDetailsRepository: TripDetailsRepository,
    ) : Actor<Intent, Effect, State> {
        override fun act(action: Intent, state: State): Flow<Effect> =
            when (action) {
                is Intent.DeleteTrip -> flow {
                    emit(Effect.CloseDropdownMenu)
                    tripDetailsRepository.removeTrip(state.name)
                    emit(Effect.CloseScreen)
                }
                is Intent.CloseDropdownMenu -> flow { emit(Effect.CloseDropdownMenu) }
                is Intent.OpenDropdownMenu -> flow { emit(Effect.OpenDropdownMenu) }
                is Intent.CloseScreen -> flow { emit(Effect.CloseScreen) }
            }
    }

    object ReducerImpl : Reducer<Effect, State> {
        override fun reduce(effect: Effect, state: State): State =
            when (effect) {
                is Effect.CloseDropdownMenu -> state.copy(isDropdownMenuOpened = false)
                is Effect.OpenDropdownMenu -> state.copy(isDropdownMenuOpened = true)
                is Effect.CloseScreen -> state
            }
    }

    object NewsPublisherImpl : NewsPublisher<Intent, Effect, State, News> {
        override fun publish(action: Intent, effect: Effect, state: State): News? =
            when (effect) {
                is Effect.CloseScreen -> News.CloseScreen
                is Effect.CloseDropdownMenu,
                is Effect.OpenDropdownMenu -> null
            }
    }
}