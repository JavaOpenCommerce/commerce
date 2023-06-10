plugins {
    groovy
}

group = "com.example.jcc.database"


dependencies {
    implementation(project(":bom"))

    implementation(project(":database:apid"))
    implementation(project(":app"))
    implementation(project(":model"))
    implementation(project(":commons"))

    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("io.smallrye.reactive:mutiny")

//    TODO: move redis integration to separate module.
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-redis-client:0.0.16")

    implementation(libs.log4j.api)
    implementation(libs.log4j.core)

    // bundle dependencies for using Spock
    testImplementation(libs.bundles.spock)
}

