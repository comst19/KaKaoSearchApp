import java.util.Properties

plugins {
    alias(libs.plugins.comst.android.application)
}

val properties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
properties.load(localPropertiesFile.inputStream())

android {
    namespace = "com.comst.kakaosearchapp"

    defaultConfig {
        applicationId = "com.comst.kakaosearchapp"
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["MANI"] = properties["not_string"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.feature.navigator)
    implementation(projects.data)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}