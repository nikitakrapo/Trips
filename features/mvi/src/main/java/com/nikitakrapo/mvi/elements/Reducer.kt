package com.nikitakrapo.mvi.elements

interface Reducer<Effect : Any, State : Any> {

    fun reduce(effect: Effect, state: State): State
}