package com.nikitakrapo.login

import com.nikitakrapo.login.MainLoginFeature.Intent
import com.nikitakrapo.login.MainLoginFeature.News
import com.nikitakrapo.login.MainLoginFeature.State
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.ReducerFeature

class MainLoginFeature : ReducerFeature<Intent, State, News>(
    initialState = State(),
    reducer = ReducerImpl,
) {

    sealed class Intent {
        class GoToRegister(val prefilledEmail: String?) : Intent()
        object CloseScreen : Intent()
    }

    data class State(
        val currentScreen: LoginScreen = LoginScreen.LogIn,
    )

    sealed class LoginScreen {
        object LogIn : LoginScreen()
        object Register : LoginScreen()
    }

    sealed class News {
        object CloseScreen : News()
    }

    private object ReducerImpl : Reducer<Intent, State> {
        override fun reduce(effect: Intent, state: State): State =
            when (effect) {
                is Intent.GoToRegister -> state.copy(currentScreen = LoginScreen.Register)
                is Intent.CloseScreen -> state
            }
    }

    private object NewsPublisherImpl : IntentNewsPublisher<Intent, State, News>() {
        override fun invoke(intent: Intent, state: State): News? =
            when (intent) {
                is Intent.CloseScreen -> News.CloseScreen
                is Intent.GoToRegister -> null
            }
    }
}