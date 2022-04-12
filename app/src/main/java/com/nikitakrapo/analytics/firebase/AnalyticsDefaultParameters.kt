package com.nikitakrapo.analytics.firebase

import android.os.Bundle
import com.nikitakrapo.analytics.firebase.AnalyticsDefaultParameters.Companion.PARAM_APP_VERSION
import com.nikitakrapo.analytics.firebase.AnalyticsDefaultParameters.Companion.PARAM_ENVIRONMENT

class AnalyticsDefaultParameters(
    val environment: AnalyticsEnvironment,
    val version: String,

) {
    companion object {
        const val PARAM_ENVIRONMENT = "Environment"
        const val PARAM_APP_VERSION = "Version"
    }
}

fun AnalyticsDefaultParameters.toBundle(): Bundle {
    return Bundle().apply {
        putString(PARAM_ENVIRONMENT, this@toBundle.environment.value)
        putString(PARAM_APP_VERSION, this@toBundle.version)
    }
}

enum class AnalyticsEnvironment(val value: String) {
    PRODUCTION("Production"), TESTING("Testing")
}