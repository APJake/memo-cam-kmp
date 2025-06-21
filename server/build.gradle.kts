plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "com.logixowl.memocam"
version = "1.0.0"
application {
    mainClass.set("com.logixowl.memocam.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)

    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.server.auth.core)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.pages)
    implementation(libs.ktor.server.call.logging)

    implementation(libs.ktor.mongo.core)
    implementation(libs.ktor.mongo.coroutine)
    implementation(libs.ktor.jbcrypt)

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}