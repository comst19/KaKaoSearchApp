plugins {
    alias(libs.plugins.comst.android.application)
}

android {
    namespace = "com.comst.kakaosearchapp"

    defaultConfig {
        applicationId = "com.comst.kakaosearchapp"
        versionCode = 1
        versionName = "1.0"
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

configurations.implementation{
    exclude(group = "com.intellij", module = "annotations")
}

dependencies {
    implementation(projects.feature.navigator)
    implementation(projects.data)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
    // When using Kotlin.
    ksp("androidx.hilt:hilt-compiler:1.2.0")
}