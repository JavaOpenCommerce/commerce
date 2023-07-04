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

    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation(libs.jackson.databind)


    implementation(libs.log4j.api)
    implementation(libs.log4j.core)

    // bundle dependencies for using Spock
    testImplementation(libs.bundles.spock)
}

