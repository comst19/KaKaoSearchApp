plugins {
    alias(libs.plugins.comst.android.library)
    alias(libs.plugins.comst.android.library.compose)
}

android {
    namespace = "com.comst.designsystem"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation (libs.accompanist.systemuicontroller)
    implementation(libs.coil.compose)
    implementation(libs.lottie)
}
