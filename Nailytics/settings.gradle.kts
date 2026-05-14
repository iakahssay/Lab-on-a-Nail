pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral() // Ensure that Maven Central is included

        // Jitpack repo needed for Nix Universal SDK cross-dependencies
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Nailytics"
include(":app")
