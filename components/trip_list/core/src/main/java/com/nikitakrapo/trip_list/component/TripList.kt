package com.nikitakrapo.trip_list.component

import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.Bootstrapper
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import com.nikitakrapo.trip_list.component.TripList.Effect
import com.nikitakrapo.trip_list.component.TripList.Intent
import com.nikitakrapo.trip_list.component.TripList.News
import com.nikitakrapo.trip_list.component.TripList.State
import com.nikitakrapo.trip_list.data.TripListRepository
import com.nikitakrapo.trip_list.dto.TripModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TripList(
    tripListRepository: TripListRepository,
) : BaseFeature<Intent, Intent, Effect, State, News>(
    initialState = State(),
    intentToAction = { it },
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

    data class State(
        val tripList: List<TripModel> = emptyList(),
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
    ) : Actor<Intent, Effect, State> {
        override fun act(action: Intent, state: State): Flow<Effect> =
            when (action) {
                is Intent.OpenAddTrip -> flow { emit(Effect.OpenAddTrip) }
                is Intent.OpenTripDetails -> flow { emit(Effect.OpenTripDetails(action.name)) }
                is Intent.RefreshTrips -> flow {
                    emit(Effect.UpdateIsRefreshing(true))
                    val tripList = tripListRepository.getTripList()
                    emit(Effect.UpdateTripList(tripList))
                    emit(Effect.UpdateIsRefreshing(false))
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

    object NewsPublisherImpl : NewsPublisher<Intent, Effect, State, News> {
        override fun publish(action: Intent, effect: Effect, state: State): News? =
            when (effect) {
                is Effect.OpenAddTrip -> News.OpenAddTrip
                is Effect.OpenTripDetails -> News.OpenDetails(effect.name)
                is Effect.UpdateIsRefreshing, is Effect.UpdateTripList -> null
            }
    }

    object BootstrapperImpl : Bootstrapper<Intent> {
        override fun bootstrap(): Flow<Intent> =
            flow { emit(Intent.RefreshTrips) }
    }
}
