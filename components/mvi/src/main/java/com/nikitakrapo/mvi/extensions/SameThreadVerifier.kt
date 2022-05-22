package com.nikitakrapo.mvi.extensions

class SameThreadVerifier(
    private val originalThread: Thread = Thread.currentThread()
) {

    fun verify() {
        assert(originalThread.id == Thread.currentThread().id) {
            "Wrong thread"
        }
    }
}