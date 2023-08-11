plugins {
    groovy
}

group = "com.example.jcc.adapters"


dependencies {
    implementation(project(":bom"))

    implementation(project(":app"))
    implementation(project(":model"))
    implementation(project(":commons"))

    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("jakarta.json.bind:jakarta.json.bind-api")
    implementation("io.quarkus:quarkus-redis-client")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-web-client")
    implementation(libs.jackson.databind)
    implementation(libs.elasticsearch)


    implementation(libs.log4j.api)
    implementation(libs.log4j.core)

    // bundle dependencies for using Spock
    testImplementation(libs.bundles.spock)
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}

