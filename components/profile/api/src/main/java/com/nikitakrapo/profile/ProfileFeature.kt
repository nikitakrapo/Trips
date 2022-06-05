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
            Intent.OpenLogin -> Action.OpenLogin
            Intent.OpenRegistration -> Action.OpenRegistration
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
        object OpenLogin : Intent()
        object OpenRegistration : Intent()
        object SignOut : Intent()
        object RemoveAccount : Intent()
    }

    sealed class Action {
        object OpenLogin : Action()
        object OpenRegistration : Action()
        object SignOut : Action()
        class UpdateAccount(val account: Account?) : Action()
        object RemoveAccount : Action()
    }

    sealed class Effect {
        object OpenLogin : Effect()
        object OpenRegistration : Effect()
        class AccountUpdated(val user: ProfileAccount?) : Effect()
    }

    sealed class State {
        data class Authorized(
            val authorizedUser: ProfileAccount
        ) : State()

        object Unauthorized : State()
    }

    sealed class News {
        object OpenLogin : News()
        object OpenRegistration : News()
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
                is Action.OpenLogin ->
                    flow { emit(Effect.OpenLogin) }
                is Action.OpenRegistration ->
                    flow { emit(Effect.OpenRegistration) }
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
                is Effect.OpenLogin,
                is Effect.OpenRegistration -> state
            }
    }

    private object NewsPublisherImpl : NewsPublisher<Action, Effect, State, News> {
        override fun publish(action: Action, effect: Effect, state: State): News? =
            when (effect) {
                is Effect.OpenLogin -> News.OpenLogin
                is Effect.OpenRegistration -> News.OpenRegistration
                is Effect.AccountUpdated -> null
            }
    }
}