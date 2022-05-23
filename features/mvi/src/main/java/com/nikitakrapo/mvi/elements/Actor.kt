package com.nikitakrapo.mvi.elements

import kotlinx.coroutines.flow.Flow

interface Actor<Action : Any, Effect : Any, State : Any> {

    fun act(action: Action, state: State): Flow<Effect>
}