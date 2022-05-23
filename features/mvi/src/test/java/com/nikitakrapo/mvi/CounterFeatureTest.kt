package com.nikitakrapo.mvi

import com.nikitakrapo.coroutines.MainDispatcherRule
import com.nikitakrapo.mvi.CounterFeatureTest.CounterFeature.Effect
import com.nikitakrapo.mvi.CounterFeatureTest.CounterFeature.Intent
import com.nikitakrapo.mvi.CounterFeatureTest.CounterFeature.State
import com.nikitakrapo.mvi.elements.Actor
import com.nikitakrapo.mvi.elements.Reducer
import com.nikitakrapo.mvi.feature.BaseFeature
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CounterFeatureTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var counterFeature: CounterFeature

    @Before
    fun setup() {
        counterFeature = CounterFeature()
    }

    @Test
    fun `correct initial state after init`() {
        assertTrue(counterFeature.state.value == DEFAULT_STATE)
    }

    @Test
    fun `incremented state on Increment intent`() {
        counterFeature.accept(Intent.Increment)

        assertEquals(1, counterFeature.state.value.count)
    }

    @Test
    fun `decremented state on Decrement intent`() {
        counterFeature.accept(Intent.Decrement)

        assertEquals(-1, counterFeature.state.value.count)
    }

    @After
    fun teardown() {
        counterFeature.dispose()
    }

    class CounterFeature : BaseFeature<Intent, Intent, Effect, State, Nothing>(
        initialState = DEFAULT_STATE,
        intentToAction = { it },
        actor = ActorImpl,
        reducer = ReducerImpl,
    ) {

        sealed class Intent {
            object Increment : Intent()
            object Decrement : Intent()
        }

        sealed class Effect {
            object PlusCount : Effect()
            object MinusCount : Effect()
        }

        data class State(
            val count: Int
        )
    }

    object ActorImpl : Actor<Intent, Effect, State> {
        override fun act(action: Intent, state: State): Flow<Effect> {
            println("impl on act")
            return when (action) {
                Intent.Decrement -> flow { emit(Effect.MinusCount) }
                Intent.Increment -> flow { emit(Effect.PlusCount) }
            }
        }
    }

    object ReducerImpl : Reducer<Effect, State> {
        override fun reduce(effect: Effect, state: State): State =
            when (effect) {
                Effect.MinusCount -> state.copy(count = state.count - 1)
                Effect.PlusCount -> state.copy(count = state.count + 1)
            }
    }

    private companion object {
        val DEFAULT_STATE = State(count = 0)
    }
}