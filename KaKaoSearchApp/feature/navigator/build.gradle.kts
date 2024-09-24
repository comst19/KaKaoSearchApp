plugins {
    alias(libs.plugins.comst.android.feature.compose)
}

android {
    namespace = "com.comst.navigator"
}


dependencies {

    // 각 feature
    implementation(projects.feature.signin)
    implementation(projects.feature.home)
    implementation(projects.feature.searchCustomPaging)
    implementation(projects.feature.favoriteSharedPreferences)

    implementation(libs.permissions)

}