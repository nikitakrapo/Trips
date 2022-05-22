package com.nikitakrapo.mvi.feature

import com.nikitakrapo.mvi.coroutines.CoroutineDispatchers
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.Bootstrapper
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.extensions.SameThreadVerifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

open class BaseFeature<Intent : Any, Action : Any, Effect : Any, State : Any, News : Any>(
    initialState: State,
    private val dispatchers: CoroutineDispatchers = CoroutineDispatchers(),
    private val intentToAction: (Intent) -> Action,
    bootstrapper: Bootstrapper<Action>? = null,
    actor: Actor<Action, Effect, State>,
    reducer: Reducer<Effect, State>,
    newsPublisher: NewsPublisher<Action, Effect, State, News>? = null,
) : Feature<Intent, State, News> {

    private val sameThreadVerifier = SameThreadVerifier()

    private val featureScope = CoroutineScope(dispatchers.main)

    private val actorInternalWrapper = ActorInternalWrapper(actor)
    private val reducerInternalWrapper = ReducerInternalWrapper(reducer)
    private val newsPublisherInternalWrapper = NewsPublisherInternalWrapper(newsPublisher)

    private val _state: MutableStateFlow<State> =
        MutableStateFlow(initialState)
    override val state: StateFlow<State> = _state

    private val _news: MutableSharedFlow<News> =
        MutableSharedFlow()
    override val news: SharedFlow<News> = _news

    private val actions: MutableSharedFlow<Action> = MutableSharedFlow()

    init {
        featureScope.launch(Dispatchers.Unconfined) {
            actions.collect { action ->
                actorInternalWrapper.act(action, state.value)
            }
        }

        bootstrapper?.let {
            featureScope.launch {
                it.bootstrap().collect { action ->
                    actorInternalWrapper.act(action, state.value)
                }
            }
        }
    }

    override fun accept(intent: Intent) {
        featureScope.launch(Dispatchers.Unconfined) { actions.emit(intentToAction(intent)) }
    }

    override fun dispose() {
        featureScope.cancel(CancellationException("Feature is disposed"))
    }

    private inner class ActorInternalWrapper(
        private val actor: Actor<Action, Effect, State>,
    ) {
        fun act(action: Action, state: State) {
            featureScope.launch(dispatchers.mainImmediate) {
                actor.act(action, state).collect { effect ->
                    reducerInternalWrapper.reduce(
                        action,
                        effect,
                        _state.value
                    )
                }
            }
        }
    }

    private inner class ReducerInternalWrapper(
        private val reducer: Reducer<Effect, State>
    ) {
        fun reduce(action: Action, effect: Effect, state: State) {
            sameThreadVerifier.verify()

            val newState = reducer.reduce(effect, state)
            _state.value = newState
            newsPublisherInternalWrapper.publish(action, effect, state)
        }
    }

    private inner class NewsPublisherInternalWrapper(
        private val newsPublisher: NewsPublisher<Action, Effect, State, News>?
    ) {
        fun publish(action: Action, effect: Effect, state: State) {
            newsPublisher?.publish(action, effect, state)?.let { news ->
                featureScope.launch {
                    _news.emit(news)
                }
            }
        }
    }
}