package com.nikitakrapo.mvi.feature

import com.nikitakrapo.mvi.coroutines.CoroutineDispatchers
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.Bootstrapper
import com.nikitakrapo.mvi.elements.NewsPublisher
import com.nikitakrapo.mvi.elements.Reducer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class ReducerFeature<Intent : Any, State : Any, News : Any>(
    initialState: State,
    reducer: Reducer<Intent, State>,
    bootstrapper: Bootstrapper<Intent>? = null,
    newsPublisher: IntentNewsPublisher<Intent, State, News>? = null,
    coroutineDispatchers: CoroutineDispatchers = CoroutineDispatchers(),
) : BaseFeature<Intent, Intent, Intent, State, News>(
    initialState = initialState,
    bootstrapper = bootstrapper,
    intentToAction = { it },
    actor = BypassActor(),
    reducer = reducer,
    newsPublisher = newsPublisher,
    dispatchers = coroutineDispatchers
) {
    class BypassActor<Intent : Any, State : Any> : Actor<Intent, Intent, State> {
        override fun act(action: Intent, state: State): Flow<Intent> =
            flow { emit(action) }
    }

    // TODO: think about naming
    abstract class IntentNewsPublisher<Intent : Any, State : Any, News : Any> :
        NewsPublisher<Intent, Intent, State, News> {
        override fun publish(action: Intent, effect: Intent, state: State): News? =
            invoke(action, state)

        abstract fun invoke(intent: Intent, state: State): News?
    }
}