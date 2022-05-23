package com.nikitakrapo.mvi.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchers(
    val main: CoroutineDispatcher = Dispatchers.Main,
    val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate,
    val io: CoroutineDispatcher = Dispatchers.IO,
    val default: CoroutineDispatcher = Dispatchers.Default,
)
