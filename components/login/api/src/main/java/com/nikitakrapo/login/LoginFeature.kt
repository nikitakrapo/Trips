package com.nikitakrapo.login

import com.nikitakrapo.am.AccountManager
import com.nikitakrapo.am.AccountManager.AuthorizationResult
import com.nikitakrapo.login.LoginFeature.Effect
import com.nikitakrapo.login.LoginFeature.Intent
import com.nikitakrapo.login.LoginFeature.News
import com.nikitakrapo.login.LoginFeature.State
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class LoginFeature(
    accountManager: AccountManager,
    prewrittenEmail: String = "",
) : BaseFeature<Intent, Intent, Effect, State, News>(
    initialState = State(emailText = prewrittenEmail),
    intentToAction = { it },
    actor = ActorImpl(accountManager),
    reducer = ReducerImpl,
    newsPublisher = NewsPublisherImpl,
) {

    sealed class Intent {
        class ChangeEmailText(val text: String) : Intent()
        class ChangePasswordText(val text: String) : Intent()
        object ChangePassportVisibility : Intent()
        object PerformLogin : Intent()
    }

    sealed class Effect {
        class EmailTextChanged(val text: String) : Effect()
        class PasswordTextChanged(val text: String) : Effect()
        class ChangePassportVisibility(val isVisible: Boolean) : Effect()

        object StartedLoggingIn : Effect()
        class FinishedLoggingIn(val result: AuthorizationResult) : Effect()
        class UpdateLoginError(val e: Exception?) : Effect()
    }

    data class State(
        val emailText: String = "",
        val passwordText: String = "",
        val isPasswordVisible: Boolean = false,
        val isPerformingLogin: Boolean = false,
        val loginError: String? = null,
        val isLoggingIn: Boolean = false,
    )

    sealed class News {
        object CloseScreen : News()
    }

    private class ActorImpl(
        private val accountManager: AccountManager,
    ) : Actor<Intent, Effect, State> {
        override fun act(action: Intent, state: State): Flow<Effect> =
            when (action) {
                is Intent.ChangeEmailText ->
                    flow {
                        emit(Effect.UpdateLoginError(null))
                        emit(Effect.EmailTextChanged(action.text))
                    }
                is Intent.ChangePasswordText ->
                    flow {
                        emit(Effect.UpdateLoginError(null))
                        emit(Effect.PasswordTextChanged(action.text))
                    }
                is Intent.ChangePassportVisibility ->
                    flow { emit(Effect.ChangePassportVisibility(!state.isPasswordVisible)) }
                is Intent.PerformLogin -> flow {
                    emit(Effect.StartedLoggingIn)
                    emit(Effect.UpdateLoginError(null))
                    val result = accountManager.signInUserByEmailPassword(
                        email = state.emailText,
                        password = state.passwordText
                    )
                    emit(Effect.FinishedLoggingIn(result))
                }
            }
    }

    private object ReducerImpl : Reducer<Effect, State> {
        override fun reduce(effect: Effect, state: State): State =
            when (effect) {
                is Effect.EmailTextChanged -> state.copy(emailText = effect.text)
                is Effect.PasswordTextChanged -> state.copy(passwordText = effect.text)
                is Effect.ChangePassportVisibility -> state.copy(isPasswordVisible = effect.isVisible)
                //FIXME: normal message (probably make LoginError enum)
                is Effect.UpdateLoginError -> state.copy(loginError = effect.e?.message)
                is Effect.FinishedLoggingIn -> {
                    val notLoggingInState = state.copy(isLoggingIn = false)
                    if (effect.result is AuthorizationResult.Error)
                        notLoggingInState.copy(loginError = effect.result.error.message)
                    else notLoggingInState
                }
                is Effect.StartedLoggingIn -> state.copy(isLoggingIn = true)
            }
    }

    private object NewsPublisherImpl : NewsPublisher<Intent, Effect, State, News> {
        override fun publish(action: Intent, effect: Effect, state: State): News? =
            when (effect) {
                is Effect.FinishedLoggingIn -> {
                    if (effect.result is AuthorizationResult.Success)
                        News.CloseScreen
                    else null
                }
                is Effect.StartedLoggingIn,
                is Effect.EmailTextChanged,
                is Effect.UpdateLoginError,
                is Effect.PasswordTextChanged,
                is Effect.ChangePassportVisibility -> null
            }
    }

    companion object {
        private val CLOSE_AFTER_LOGIN_DELAY = 1000L.milliseconds
    }
}