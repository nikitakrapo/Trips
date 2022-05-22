plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(KotlinLib.std)
    implementation(KotlinLib.coroutines)

    implementation(project(Features.mvi))

    testImplementation(JUnit.jUnit)
    testImplementation(Mock.mockK)
}
