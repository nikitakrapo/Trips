package com.nikitakrapo.android.trips

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.atomic.AtomicReference

@OptIn(InternalCoroutinesApi::class)
fun <A, B: Any, R> Flow<A>.withLatestFrom(other: Flow<B>, transform: suspend (A, B) -> R): Flow<R> = flow {
    coroutineScope {
        val latestB = AtomicReference<B?>()
        val outerScope = this
        launch {
            try {
                other.onEach {
                    latestB.set(it)
                }
            } catch(e: CancellationException) {
                outerScope.cancel(e) // cancel outer scope on cancellation exception, too
            }
        }
        onEach { a: A ->
            latestB.get()?.let { b -> emit(transform(a, b)) }
        }
    }
}