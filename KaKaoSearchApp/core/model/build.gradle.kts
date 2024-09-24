plugins {
    alias(libs.plugins.comst.java.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies{
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
}
