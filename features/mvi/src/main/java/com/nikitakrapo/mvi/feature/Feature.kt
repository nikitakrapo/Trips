package com.nikitakrapo.mvi.feature

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface Feature<Intent : Any, State : Any, News : Any> {

    val state: StateFlow<State>

    val news: SharedFlow<News>

    fun accept(intent: Intent)

    fun dispose()
}