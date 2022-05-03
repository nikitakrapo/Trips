package com.nikitakrapo.trips.analytics

import com.nikitakrapo.trips.analytics.firebase.FirebaseProvider
import javax.inject.Inject

class AnalyticsManager @Inject constructor(
    private val firebaseProvider: FirebaseProvider
) {

}