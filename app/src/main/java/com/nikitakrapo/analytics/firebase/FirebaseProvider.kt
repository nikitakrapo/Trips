package com.nikitakrapo.analytics.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.nikitakrapo.analytics.Events
import javax.inject.Inject

class FirebaseProvider @Inject constructor() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    fun init(
        context: Context,
        analyticsDefaultParameters: AnalyticsDefaultParameters,
    ) {
        FirebaseApp.initializeApp(context)
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        firebaseAnalytics.setDefaultEventParameters(analyticsDefaultParameters.toBundle())
    }

    fun reportEvent(event: String) {
        firebaseAnalytics.logEvent(event, null)
    }

    fun reportAttributedEvent(event: String, params: Bundle) {
        firebaseAnalytics.logEvent(event, params)
    }

    fun reportAppOpen() {
        reportEvent(Events.APP_OPEN)
    }
}