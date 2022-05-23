package com.nikitakrapo.mvi.elements

interface NewsPublisher<Action : Any, Effect : Any, State : Any, News : Any> {

    fun publish(action: Action, effect: Effect, state: State): News?
}