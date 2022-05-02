object KotlinLib {
    const val std = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Config.kotlin}"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Config.kotlin}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
}

object AndroidX {
    const val coreCtx = "androidx.core:core-ktx:${Versions.coreCtx}"

    const val appCompat = "androidx.appcompat:appcompat:1.4.1"

    const val lifecycleRuntimeCtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeCtx}"

    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val coreSplashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"
}

object JetpackCompose {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayoutCompose}"
    const val material3 = "androidx.compose.material3:material3:${Versions.material3Compose}"
    const val materialIcons = "androidx.compose.material:material-icons-core:${Versions.compose}"
}

object MviKotlin {
    const val core = "com.arkivanov.mvikotlin:mvikotlin:${Versions.mviKotlin}"
    const val main = "com.arkivanov.mvikotlin:mvikotlin-main:${Versions.mviKotlin}"
    const val logging = "com.arkivanov.mvikotlin:mvikotlin-logging:${Versions.mviKotlin}"
    const val timeTravel = "com.arkivanov.mvikotlin:mvikotlin-timetravel:${Versions.mviKotlin}"
    const val coroutinesExtensions = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${Versions.mviKotlin}"
    const val rx = "com.arkivanov.mvikotlin:rx:${Versions.mviKotlin}"
}

object Wear {
    const val wear = "androidx.wear:wear:${Versions.wear}"
    const val composeMaterial = "androidx.wear.compose:compose-material:${Versions.wearCompose}"
    const val composeFoundation = "androidx.wear.compose:compose-foundation:${Versions.wearCompose}"
    const val composeNavigation = "androidx.wear.compose:compose-navigation:${Versions.wearCompose}"
}

object JakeWharton {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object AndroidXTest {
    const val androidExtJUnit = "androidx.test.ext:junit:${Versions.androidExtJUnit}"
}

object Google {
    const val dagger2 = "com.google.dagger:dagger:${Versions.dagger2}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger2}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val androidMaterial = "com.google.android.material:material:${Versions.googleAndroidMaterial}"
    const val accompanistSystemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
    const val accompanistSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
    const val accompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}"
    const val accompanistNavigationMaterial = "com.google.accompanist:accompanist-navigation-material:${Versions.accompanist}"
    const val accompanistInsets = "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
    const val maps = "com.google.maps.android:maps-compose:${Versions.maps}"
    const val playServicesMaps = "com.google.android.gms:play-services-maps:${Versions.playServicesMaps}"

    const val firebaseBoM = "com.google.firebase:firebase-bom:${Versions.firebaseBoM}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
}

object Landscapist {
    const val glide = "com.github.skydoves:landscapist-glide:${Versions.landscapistGlide}"
}

object JUnit {
    const val jUnit = "junit:junit:${Versions.jUnit}"
}

object Espresso {
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}
