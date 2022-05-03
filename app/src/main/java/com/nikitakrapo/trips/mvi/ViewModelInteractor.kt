package com.nikitakrapo.trips.mvi

import kotlinx.coroutines.flow.StateFlow

/**
 *
 * @param S state
 * @param E event
 * @param A action
 */
interface ViewModelInteractor<S, E, A> {

    val actions: StateFlow<A?>

    val uiState: StateFlow<S>

    fun reduce(previousState: S, event: E): S
}