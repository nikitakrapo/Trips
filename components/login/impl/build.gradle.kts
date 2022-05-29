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

//FIXME: ^ move this to separate gradle script ^

dependencies {
    implementation(project(Features.mvi))
    implementation(project(Features.tripsDesign))
    implementation(project(Features.accountManagerImpl))
    implementation(project(Components.loginCore))

    implementation(AndroidX.coreCtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleRuntimeCtx)
    implementation(AndroidX.activityCompose)

    implementation(JetpackCompose.material)
    implementation(JetpackCompose.uiToolingPreview)
    implementation(JetpackCompose.ui)
    implementation(JetpackCompose.constraintLayout)
    implementation(JetpackCompose.material3)
    implementation(JetpackCompose.navigation)
    implementation(JetpackCompose.materialIconsExtended)

    implementation(Google.hilt)
    implementation(Google.hiltNavigationCompose)
    kapt(Google.hiltCompiler)
    implementation(Google.androidMaterial)
    implementation(Google.accompanistNavigationAnimation)
    implementation(Google.accompanistNavigationMaterial)

    implementation(platform(Google.firebaseBoM))
    implementation(Google.firebaseAnalytics)
    implementation(Google.firebaseCrashlytics)

    implementation(JakeWharton.timber)

    testImplementation(JUnit.jUnit)

    androidTestImplementation(AndroidXTest.androidExtJUnit)
}