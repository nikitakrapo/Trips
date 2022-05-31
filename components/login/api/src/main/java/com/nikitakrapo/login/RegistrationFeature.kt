package com.nikitakrapo.login

import com.nikitakrapo.am.AccountManager
import com.nikitakrapo.login.RegistrationFeature.Effect
import com.nikitakrapo.login.RegistrationFeature.Intent
import com.nikitakrapo.login.RegistrationFeature.News
import com.nikitakrapo.login.RegistrationFeature.State
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegistrationFeature(
    accountManager: AccountManager,
) : BaseFeature<Intent, Intent, Effect, State, News>(
    initialState = State(),
    intentToAction = { it },
    actor = ActorImpl(accountManager),
    reducer = ReducerImpl,
    newsPublisher = NewsPublisherImpl
) {

    sealed class Intent {
        class ChangeEmailText(val text: String) : Intent()
        class ChangePasswordText(val text: String) : Intent()
        object ChangePasswordVisibility : Intent()
        object PerformRegistration : Intent()

        object GoBack : Intent()
    }

    sealed class Effect {
        class EmailTextChanged(val text: String) : Effect()
        class PasswordTextChanged(val text: String) : Effect()
        class ChangePasswordVisibility(val isVisible: Boolean) : Effect()

        object StartedRegistration : Effect()
        class FinishedRegistration(val result: AccountManager.AuthorizationResult) : Effect()
        class UpdateRegistrationError(val e: Exception?) : Effect()

        object CloseScreen : Effect()
    }

    data class State(
        val emailText: String = "",
        val passwordText: String = "",
        val isPasswordVisible: Boolean = false,
        val loginError: String? = null,
        val isRegistering: Boolean = false,
    )

    sealed class News {
        object CloseScreen : News()
        object CloseAuthorization : News()
    }

    private class ActorImpl(
        private val accountManager: AccountManager,
    ) : Actor<Intent, Effect, State> {
        override fun act(action: Intent, state: State): Flow<Effect> =
            when (action) {
                is Intent.ChangeEmailText ->
                    flow { emit(Effect.EmailTextChanged(action.text)) }
                is Intent.ChangePasswordText ->
                    flow { emit(Effect.PasswordTextChanged(action.text)) }
                is Intent.ChangePasswordVisibility ->
                    flow { emit(Effect.ChangePasswordVisibility(!state.isPasswordVisible)) }
                is Intent.PerformRegistration ->
                    flow {
                        emit(Effect.StartedRegistration)
                        emit(Effect.UpdateRegistrationError(null))
                        val result = accountManager.createUserByEmailPassword(
                            email = state.emailText,
                            password = state.passwordText
                        )
                        emit(Effect.FinishedRegistration(result))
                        if (result is AccountManager.AuthorizationResult.Error) {
                            emit(Effect.UpdateRegistrationError(result.error))
                        }
                    }
                is Intent.GoBack ->
                    flow { emit(Effect.CloseScreen) }
            }
    }

    private object ReducerImpl : Reducer<Effect, State> {
        override fun reduce(effect: Effect, state: State): State =
            when (effect) {
                is Effect.EmailTextChanged -> state.copy(emailText = effect.text)
                is Effect.PasswordTextChanged -> state.copy(passwordText = effect.text)
                is Effect.ChangePasswordVisibility -> state.copy(isPasswordVisible = effect.isVisible)
                is Effect.UpdateRegistrationError -> state.copy(loginError = effect.e?.message)
                is Effect.StartedRegistration -> state.copy(isRegistering = true)
                is Effect.FinishedRegistration -> state.copy(isRegistering = false)
                is Effect.CloseScreen -> state
            }
    }

    private object NewsPublisherImpl : NewsPublisher<Intent, Effect, State, News> {
        override fun publish(action: Intent, effect: Effect, state: State): News? =
            when (effect) {
                is Effect.FinishedRegistration -> {
                    if (effect.result is AccountManager.AuthorizationResult.Success)
                        News.CloseAuthorization
                    else null
                } //FIXME: inform user that he's registered
                is Effect.CloseScreen -> News.CloseScreen
                is Effect.ChangePasswordVisibility,
                is Effect.EmailTextChanged,
                is Effect.PasswordTextChanged,
                is Effect.StartedRegistration,
                is Effect.UpdateRegistrationError -> null
            }
    }
}