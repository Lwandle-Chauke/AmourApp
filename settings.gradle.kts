// This file defines repository management and project structure for Gradle.

pluginManagement {
    // Repositories for resolving Gradle plugins (like Android, Kotlin, Firebase, etc.)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // Prevents subprojects from declaring their own repositories
    // (forces all repos to be declared here instead)
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    // Global repositories used by all modules
    repositories {
        google()
        mavenCentral()
    }
}

// Root project name (the name of the project in Android Studio)
rootProject.name = "Amour"

// Includes the app module in the project (we could add more later if needed)
include(":app")
