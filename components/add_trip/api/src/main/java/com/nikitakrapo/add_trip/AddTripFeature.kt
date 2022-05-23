package com.nikitakrapo.add_trip

import com.nikitakrapo.add_trip.AddTripFeature.Effect
import com.nikitakrapo.add_trip.AddTripFeature.Intent
import com.nikitakrapo.add_trip.AddTripFeature.News
import com.nikitakrapo.add_trip.AddTripFeature.State
import com.nikitakrapo.data.AddTripRepository
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import com.nikitakrapo.validators.TripNameTextValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddTripFeature(
    addTripRepository: AddTripRepository,
    tripNameTextValidator: TripNameTextValidator,
) : BaseFeature<Intent, Intent, Effect, State, News>(
    initialState = State(),
    intentToAction = { it },
    actor = ActorImpl(
        addTripRepository,
        tripNameTextValidator
    ),
    reducer = ReducerImpl,
    newsPublisher = NewsPublisherImpl
) {

    data class State(
        val nameText: String = "",
        val nameError: TripNameError? = null, //TODO: make this more abstract - smth like textFieldError
        val isAddButtonEnabled: Boolean = false,
        val isAdding: Boolean = false,
    )

    sealed class Intent {
        class ChangeNameText(val text: String) : Intent()
        object AddTrip : Intent()
        object CloseScreen : Intent()
    }

    sealed class Effect {
        class UpdateNameText(val text: String) : Effect()
        class UpdateNameError(val error: TripNameError?) : Effect()
        class UpdateAddButtonEnabled(val isEnabled: Boolean) : Effect()
        class UpdateIsAdding(val isAdding: Boolean) : Effect()
        object ScreenClosed : Effect()
    }

    sealed class TripNameError {
        class InvalidCharacters(val characters: Set<Char>) : TripNameError()
        class TooShort(val minChars: Int) : TripNameError()
        class TooLong(val maxChars: Int) : TripNameError()
    }

    sealed class News {
        object CloseScreen : News()
    }

    class ActorImpl(
        private val addTripRepository: AddTripRepository,
        private val tripNameTextValidator: TripNameTextValidator,
    ) : Actor<Intent, Effect, State> {
        override fun act(action: Intent, state: State): Flow<Effect> =
            when (action) {
                is Intent.AddTrip -> flow {
                    emit(Effect.UpdateAddButtonEnabled(false))
                    emit(Effect.UpdateIsAdding(true))
                    addTripRepository.addTrip(state.nameText)
                    emit(Effect.UpdateIsAdding(false))
                    emit(Effect.ScreenClosed)
                }
                is Intent.ChangeNameText -> flow {
                    val error = tripNameTextValidator.validate(action.text)
                    emit(Effect.UpdateNameText(action.text))
                    val shouldAddButtonBeEnabled = error == null && !state.isAdding
                    if (state.isAddButtonEnabled != shouldAddButtonBeEnabled) {
                        emit(Effect.UpdateAddButtonEnabled(error == null && !state.isAdding))
                    }
                    if (state.nameError != error) {
                        emit(Effect.UpdateNameError(error))
                    }
                }
                is Intent.CloseScreen -> flow { emit(Effect.ScreenClosed) }
            }
    }

    object ReducerImpl : Reducer<Effect, State> {
        override fun reduce(effect: Effect, state: State): State =
            when (effect) {
                is Effect.UpdateAddButtonEnabled -> state.copy(isAddButtonEnabled = effect.isEnabled)
                is Effect.UpdateNameText -> state.copy(nameText = effect.text)
                is Effect.UpdateNameError -> state.copy(nameError = effect.error)
                is Effect.UpdateIsAdding -> state.copy(isAdding = effect.isAdding)
                is Effect.ScreenClosed -> state
            }
    }

    object NewsPublisherImpl : NewsPublisher<Intent, Effect, State, News> {
        override fun publish(action: Intent, effect: Effect, state: State): News? =
            when (effect) {
                is Effect.ScreenClosed -> News.CloseScreen
                is Effect.UpdateAddButtonEnabled,
                is Effect.UpdateNameError,
                is Effect.UpdateNameText,
                is Effect.UpdateIsAdding -> null
            }
    }
}