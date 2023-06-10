@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.lombok)
}
group = "com.example.jcc.business"

dependencies {
    implementation(project(":bom"))
    implementation(project(":commons"))
    implementation(libs.commons.lang)
}
