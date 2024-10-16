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

    implementation(libs.bundles.hilt.work)

}