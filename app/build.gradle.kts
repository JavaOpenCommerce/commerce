plugins {
    application
}

dependencies {
    implementation(project(":bom"))
    implementation(project(":model"))
    implementation(project(":commons"))
    implementation(libs.commons.lang)
    implementation(libs.log4j.api)
    implementation("io.quarkus:quarkus-resteasy-jsonb")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core")
}
