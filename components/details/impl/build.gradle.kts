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
    api(project(Features.navigation))
    implementation(project(Features.trips))
    api(project(Components.tripDetailsCore))

    implementation(JetpackCompose.ui)
    implementation(JetpackCompose.material)
    implementation(JetpackCompose.material3)
    implementation(JetpackCompose.uiToolingPreview)
    implementation(JetpackCompose.constraintLayout)
    androidTestImplementation(JetpackCompose.uiTestJunit4)

    implementation(Google.hilt)
    implementation(Google.hiltNavigationCompose)
    kapt(Google.hiltCompiler)
    implementation(Google.accompanistSwipeRefresh)

    testImplementation(JUnit.jUnit)
    androidTestImplementation(Mock.mockkAndroid)
}