package com.nikitakrapo.login

import com.nikitakrapo.am.AccountManager
import com.nikitakrapo.login.LoginFeature.Effect
import com.nikitakrapo.login.LoginFeature.Intent
import com.nikitakrapo.login.LoginFeature.State
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import kotlinx.coroutines.flow.Flow

class LoginFeature(
    accountManager: AccountManager,
) : BaseFeature<Intent, Intent, Effect, State, Nothing>(
    initialState = State(),
    intentToAction = { it },
    actor = ActorImpl(accountManager),
    reducer = ReducerImpl,
) {

    sealed class Intent {
        class ChangeEmailText(val text: String) : Intent()
        class ChangePasswordText(val text: String) : Intent()
        object PerformLogin : Intent()
    }

    sealed class Effect {

    }

    data class State(
        val emailText: String = "",
        val passwordText: String = "",
        val isPerformingLogin: Boolean = false,
    )

    private class ActorImpl(
        private val accountManager: AccountManager,
    ) : Actor<Intent, Effect, State> {
        override fun act(action: Intent, state: State): Flow<Effect> {
            TODO("Not yet implemented")
        }
    }

    private object ReducerImpl : Reducer<Effect, State> {
        override fun reduce(effect: Effect, state: State): State {
            TODO("Not yet implemented")
        }
    }
}