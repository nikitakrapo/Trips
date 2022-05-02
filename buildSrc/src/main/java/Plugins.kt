object Plugins {
    const val gradle = "com.android.tools.build:gradle:${Config.gradleVersion}"
    const val secretsGradle = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.secretsGradle}"

    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Config.kotlin}"

    const val googleServices = "com.google.gms:google-services:${Versions.googleServicesPlugin}"
    const val firebaseCrashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsPlugin}"
}