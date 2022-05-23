package com.nikitakrapo.mvi.elements

import kotlinx.coroutines.flow.Flow

interface Bootstrapper<Action : Any> {

    fun bootstrap(): Flow<Action>
}