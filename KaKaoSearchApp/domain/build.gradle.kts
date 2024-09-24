plugins {
    alias(libs.plugins.comst.java.library)
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.javax.inject)
    implementation(libs.paging.common)
    implementation(libs.kotlinx.coroutines.core)
}