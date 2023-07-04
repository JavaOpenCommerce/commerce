@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    groovy
}

group = "com.example.jcc.database"

dependencies {
    implementation(project(":bom"))

    implementation(project(":database:apid"))
    implementation(project(":model"))
    implementation(project(":app"))
    implementation(project(":commons"))

    implementation("io.quarkus:quarkus-redis-client")
    implementation("jakarta.json.bind:jakarta.json.bind-api")
    implementation("io.quarkus:quarkus-redis-client")

    implementation(libs.log4j.api)
    implementation(libs.log4j.core)

    // bundle dependencies for using Spock
    testImplementation(libs.bundles.spock)
}
