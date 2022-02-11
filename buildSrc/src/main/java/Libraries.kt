object AndroidXLibs {
    const val coreCtx = "androidx.core:core-ktx:${Versions.coreCtx}"

    const val appCompat = "androidx.appcompat:appcompat:1.4.1"

    const val lifecycleRuntimeCtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeCtx}"

    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
}

object JetpackComposeLibs {
    const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val material = "androidx.compose.material:material:${Versions.compose}"
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
    const val material3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
}

object AndroidXTest {
    const val androidExtJUnit = "androidx.test.ext:junit:${Versions.androidExtJUnit}"
}

object GoogleLibs {
    const val dagger2 = "com.google.dagger:dagger:${Versions.dagger2}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger2}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val androidMaterial = "com.google.android.material:material:${Versions.googleAndroidMaterial}"
    const val accompanistSystemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
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
