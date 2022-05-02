package com.nikitakrapo.analytics

import com.nikitakrapo.analytics.firebase.FirebaseProvider
import javax.inject.Inject

class AnalyticsManager @Inject constructor(
    private val firebaseProvider: FirebaseProvider
) {

}