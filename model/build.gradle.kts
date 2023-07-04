@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.lombok)
}
group = "com.example.jcc.business"

dependencies {
    implementation(project(":bom"))
    implementation(libs.commons.lang)

    testImplementation(libs.junit.api)
    testImplementation(libs.junit.engine)
    testImplementation(libs.assertj.core)
}
