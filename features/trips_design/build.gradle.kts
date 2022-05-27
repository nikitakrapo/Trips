plugins {
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
        kotlinCompilerVersion = Config.kotlin
    }
}

dependencies {
    implementation(JetpackCompose.material)
    implementation(JetpackCompose.uiToolingPreview)
    implementation(JetpackCompose.ui)
    implementation(JetpackCompose.constraintLayout)
    implementation(JetpackCompose.material3)
    debugImplementation(JetpackCompose.uiTooling)
    androidTestImplementation(JetpackCompose.uiTestJunit4)

    implementation(Google.gson)
    implementation(Google.androidMaterial)
    implementation(Google.accompanistSystemUiController)

    implementation(Landscapist.glide)

    testImplementation(JUnit.jUnit)

    androidTestImplementation(AndroidXTest.androidExtJUnit)

    androidTestImplementation(Espresso.espressoCore)
}