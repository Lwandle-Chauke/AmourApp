// ------------------- REPOSITORIES & BUILD SCRIPT -------------------
buildscript {
    repositories {
        google()       // Required for Android Gradle plugin
        mavenCentral() // Common dependencies
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.8.0")   // Android Gradle plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23") // Kotlin plugin
        classpath("com.google.gms:google-services:4.4.2")    // Firebase plugin
    }
}
