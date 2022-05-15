plugins {
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies { //FIXME: cleanup!
    implementation(project(Features.trips))
    implementation(project(Features.tripListCore))

    implementation(JetpackCompose.material)
    implementation(JetpackCompose.uiToolingPreview)
    implementation(JetpackCompose.ui)
    implementation(JetpackCompose.constraintLayout)
    implementation(JetpackCompose.material3)
    implementation(JetpackCompose.navigation)
    debugImplementation(JetpackCompose.uiTooling)
    debugImplementation(JetpackCompose.uiTestManifest)
    androidTestImplementation(JetpackCompose.uiTestJunit4)

    implementation(MviKotlin.core)
    implementation(MviKotlin.main)
    implementation(MviKotlin.logging)
    implementation(MviKotlin.timeTravel)
    implementation(MviKotlin.coroutinesExtensions)
    implementation(MviKotlin.rx)

    implementation(Google.hilt)
    implementation(Google.hiltNavigationCompose)
    kapt(Google.hiltCompiler)
    implementation(Google.gson)
    implementation(Google.androidMaterial)
    implementation(Google.accompanistSystemUiController)
    implementation(Google.accompanistSwipeRefresh)
    implementation(Google.accompanistNavigationAnimation)
    implementation(Google.accompanistNavigationMaterial)
    implementation(Google.accompanistInsets)
    implementation(Google.maps)
    implementation(Google.playServicesMaps)

    implementation(AndroidX.coreCtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleRuntimeCtx)
    implementation(AndroidX.activityCompose)
}