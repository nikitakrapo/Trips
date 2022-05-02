plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(KotlinLib.std)
    implementation(KotlinLib.coroutines)

    implementation(MviKotlin.core)
    implementation(MviKotlin.main)
    implementation(MviKotlin.logging)
    implementation(MviKotlin.timeTravel)
    implementation(MviKotlin.coroutinesExtensions)
    implementation(MviKotlin.rx)

    testImplementation(JUnit.jUnit)
}
