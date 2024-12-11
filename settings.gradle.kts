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
        mavenCentral()
    }
}

rootProject.name = "Cronos"
include(":app")
include(":core")
include(":core-ui")
include(":stopwatch")
include(":stopwatch:stopwatch_presentation")
include(":stopwatch:stopwatch_domain")
include(":stopwatch:stopwatch_data")
