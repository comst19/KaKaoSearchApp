import java.util.Properties

plugins {
    alias(libs.plugins.comst.android.library)
    alias(libs.plugins.comst.android.hilt)
}

val properties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
properties.load(localPropertiesFile.inputStream())

android {
    namespace = "com.comst.data"

    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
        buildConfigField("String", "KAKAO_BASE_URL", properties.getProperty("kakao_api_url"))
        buildConfigField("String", "KAKAO_REST_API_KEY", properties.getProperty("kakao_rest_api_key"))
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {
    implementation(projects.core.model)
    implementation(projects.domain)

    implementation(libs.bundles.retrofit)

    implementation(libs.datastore)

    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(libs.paging)

    testImplementation(libs.junit)

    implementation(libs.androidx.lifecycle.service)

    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
    // When using Kotlin.
    ksp("androidx.hilt:hilt-compiler:1.2.0")
}
