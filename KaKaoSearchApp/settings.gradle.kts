//gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
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

rootProject.name = "KaKaoSearchApp"
include(":app")
include(":domain")
include(":data")
include(":core:ui")
include(":core:designsystem")
include(":core:model")
include(":feature:navigator")
include(":feature:signin")
include(":feature:home")
include(":feature:search-custom-paging")
include(":feature:favorite-shared-preferences")
