package com.nikitakrapo.trips.analytics.firebase

import android.os.Bundle
import com.nikitakrapo.trips.analytics.firebase.AnalyticsCommonParameters.Companion.PARAM_ENVIRONMENT

class AnalyticsCommonParameters(
    val environment: AnalyticsEnvironment,
) {
    companion object {
        const val PARAM_ENVIRONMENT = "Environment"
    }
}

fun AnalyticsCommonParameters.toBundle(): Bundle {
    return Bundle().apply {
        putString(PARAM_ENVIRONMENT, this@toBundle.environment.value)
    }
}

enum class AnalyticsEnvironment(val value: String) {
    PRODUCTION("Production"), TESTING("Testing")
}