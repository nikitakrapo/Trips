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

dependencies {
    implementation(project(Features.tripsDesign))
    implementation(project(Features.trips))
    implementation(project(Components.tripListCore))

    implementation(JetpackCompose.material)
    implementation(JetpackCompose.uiToolingPreview)
    implementation(JetpackCompose.ui)
    implementation(JetpackCompose.constraintLayout)
    implementation(JetpackCompose.material3)

    implementation(project(Features.mvi))

    implementation(Google.hilt)
    implementation(Google.hiltNavigationCompose)
    kapt(Google.hiltCompiler)
    implementation(Google.androidMaterial)
    implementation(Google.accompanistSwipeRefresh)
    implementation(Google.accompanistInsets)

    implementation(AndroidX.coreCtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleRuntimeCtx)
    implementation(AndroidX.activityCompose)
}