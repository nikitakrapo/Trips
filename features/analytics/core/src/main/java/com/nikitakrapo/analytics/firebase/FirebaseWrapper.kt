package com.nikitakrapo.analytics.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

object FirebaseWrapper {
    private val scope = CoroutineScope(Dispatchers.Default)
    private var initJob: Job? = null

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val afterInitQueue: LinkedList<() -> Unit> = LinkedList()

    fun init(
        context: Context,
        analyticsCommonParameters: AnalyticsCommonParameters,
    ) {
        initJob = scope.launch {
            FirebaseApp.initializeApp(context)
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            firebaseAnalytics.setDefaultEventParameters(analyticsCommonParameters.toBundle())

            while (afterInitQueue.isNotEmpty()) {
                afterInitQueue.removeFirst().invoke()
            }
        }
    }

    fun reportEvent(event: String) = doAfterInit {
        firebaseAnalytics.logEvent(event, null)
    }

    fun reportAttributedEvent(event: String, params: Bundle) = doAfterInit {
        firebaseAnalytics.logEvent(event, params)
    }

    private fun doAfterInit(action: () -> Unit) {
        if (initJob?.isCompleted == true) {
            action()
        } else {
            afterInitQueue.add(action)
        }
    }
}