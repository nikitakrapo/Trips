package com.nikitakrapo.profile

import com.nikitakrapo.am.AccountManager
import com.nikitakrapo.am.AccountStorage
import com.nikitakrapo.dto.Account
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.Bootstrapper
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import com.nikitakrapo.profile.ProfileFeature.Action
import com.nikitakrapo.profile.ProfileFeature.Effect
import com.nikitakrapo.profile.ProfileFeature.Intent
import com.nikitakrapo.profile.ProfileFeature.News
import com.nikitakrapo.profile.ProfileFeature.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ProfileFeature(
    accountManager: AccountManager,
    accountStorage: AccountStorage,
) : BaseFeature<Intent, Action, Effect, State, News>(
    initialState = accountStorage.account?.let {
        State.Authorized(it.toProfileModel())
    } ?: State.Unauthorized,
    intentToAction = {
        when (it) {
            Intent.OpenAuthorization -> Action.OpenAuthorization
            Intent.SignOut -> Action.SignOut
            Intent.RemoveAccount -> Action.RemoveAccount
        }
    },
    bootstrapper = BootstrapperImpl(accountStorage),
    actor = ActorImpl(accountManager),
    reducer = ReducerImpl,
    newsPublisher = NewsPublisherImpl
) {

    sealed class Intent {
        object OpenAuthorization : Intent()
        object SignOut : Intent()
        object RemoveAccount : Intent()
    }

    sealed class Action {
        object OpenAuthorization : Action()
        object SignOut : Action()
        class UpdateAccount(val account: Account?) : Action()
        object RemoveAccount : Action()
    }

    sealed class Effect {
        object OpenAuthorization : Effect()
        class AccountUpdated(val user: ProfileAccount?) : Effect()
    }

    sealed class State {
        data class Authorized(
            val authorizedUser: ProfileAccount
        ) : State()

        object Unauthorized : State()
    }

    sealed class News {
        object OpenAuthorization : News()
    }

    private class BootstrapperImpl(
        private val accountStorage: AccountStorage,
    ) : Bootstrapper<Action> {
        override fun bootstrap(): Flow<Action> =
            accountStorage.accountStateFlow.map { Action.UpdateAccount(it) }
    }

    private class ActorImpl(
        private val accountManager: AccountManager,
    ) : Actor<Action, Effect, State> {
        override fun act(action: Action, state: State): Flow<Effect> =
            when (action) {
                is Action.OpenAuthorization ->
                    flow { emit(Effect.OpenAuthorization) }
                is Action.UpdateAccount ->
                    flow { emit(Effect.AccountUpdated(action.account?.toProfileModel())) }
                is Action.SignOut ->
                    flow {
                        accountManager.signOut()
                    }
                is Action.RemoveAccount ->
                    flow {
                        val result = accountManager.deleteCurrentAccount()
                        //FIXME: handle deletion error
                    }
            }
    }

    private object ReducerImpl : Reducer<Effect, State> {
        override fun reduce(effect: Effect, state: State): State =
            when (effect) {
                is Effect.AccountUpdated -> {
                    val account = effect.user
                    if (account != null) State.Authorized(effect.user) else State.Unauthorized
                }
                is Effect.OpenAuthorization -> state
            }
    }

    private object NewsPublisherImpl : NewsPublisher<Action, Effect, State, News> {
        override fun publish(action: Action, effect: Effect, state: State): News? =
            when (effect) {
                is Effect.OpenAuthorization -> News.OpenAuthorization
                is Effect.AccountUpdated -> null
            }
    }
}