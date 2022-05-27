package com.nikitakrapo.trip_list.component

import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.Bootstrapper
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import com.nikitakrapo.trip_list.component.TripListFeature.Action
import com.nikitakrapo.trip_list.component.TripListFeature.Effect
import com.nikitakrapo.trip_list.component.TripListFeature.Intent
import com.nikitakrapo.trip_list.component.TripListFeature.News
import com.nikitakrapo.trip_list.component.TripListFeature.State
import com.nikitakrapo.trip_list.data.TripListRepository
import com.nikitakrapo.trip_list.dto.TripModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TripListFeature(
    tripListRepository: TripListRepository,
) : BaseFeature<Intent, Action, Effect, State, News>(
    initialState = State(),
    intentToAction = { intent ->
        when (intent) {
            is Intent.OpenAddTrip -> Action.OpenAddTrip
            is Intent.OpenTripDetails -> Action.OpenTripDetails(intent.name)
            is Intent.RefreshTrips -> Action.RefreshTrips
        }
    },
    bootstrapper = BootstrapperImpl,
    actor = ActorImpl(tripListRepository),
    reducer = ReducerImpl,
    newsPublisher = NewsPublisherImpl
) {

    sealed class Intent {
        object RefreshTrips : Intent()
        class OpenTripDetails(val name: String) : Intent()
        object OpenAddTrip : Intent()
    }

    sealed class Action {
        object ObserveTripList : Action()
        object RefreshTrips : Action()
        class OpenTripDetails(val name: String) : Action()
        object OpenAddTrip : Action()
    }

    data class State(
        val tripList: List<TripModel>? = null,
        val isRefreshing: Boolean = false,
    )

    sealed class Effect {
        class UpdateTripList(val tripList: List<TripModel>) : Effect()
        class UpdateIsRefreshing(val isRefreshing: Boolean) : Effect()
        class OpenTripDetails(val name: String) : Effect()
        object OpenAddTrip : Effect()
    }

    sealed class News {
        class OpenDetails(val name: String) : News()
        object OpenAddTrip : News()
    }

    class ActorImpl(
        private val tripListRepository: TripListRepository,
    ) : Actor<Action, Effect, State> {
        override fun act(action: Action, state: State): Flow<Effect> =
            when (action) {
                is Action.OpenAddTrip -> flow { emit(Effect.OpenAddTrip) }
                is Action.OpenTripDetails -> flow { emit(Effect.OpenTripDetails(action.name)) }
                is Action.RefreshTrips -> flow {
                    emit(Effect.UpdateIsRefreshing(true))
                    val tripList = tripListRepository.getTripList()
                    emit(Effect.UpdateTripList(tripList))
                    emit(Effect.UpdateIsRefreshing(false))
                }
                Action.ObserveTripList -> tripListRepository.getTripListFlow().map { tripList ->
                    Effect.UpdateTripList(tripList)
                }
            }
    }

    object ReducerImpl : Reducer<Effect, State> {
        override fun reduce(effect: Effect, state: State): State =
            when (effect) {
                is Effect.UpdateIsRefreshing -> state.copy(isRefreshing = effect.isRefreshing)
                is Effect.UpdateTripList -> state.copy(tripList = effect.tripList)
                is Effect.OpenAddTrip, is Effect.OpenTripDetails -> state
            }
    }

    object NewsPublisherImpl : NewsPublisher<Action, Effect, State, News> {
        override fun publish(action: Action, effect: Effect, state: State): News? =
            when (effect) {
                is Effect.OpenAddTrip -> News.OpenAddTrip
                is Effect.OpenTripDetails -> News.OpenDetails(effect.name)
                is Effect.UpdateIsRefreshing, is Effect.UpdateTripList -> null
            }
    }

    object BootstrapperImpl : Bootstrapper<Action> {
        override fun bootstrap(): Flow<Action> =
            flow { emit(Action.ObserveTripList) }
    }
}
