@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.lombok)
}
group = "com.example.jcc.business"

dependencies {
    implementation(project(":bom"))
    implementation(project(":commons"))
    implementation(libs.commons.lang)

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("org.assertj:assertj-core:3.8.0")
}
