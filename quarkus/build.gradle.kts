@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.quarkus)
    distribution
}
group = "com.example.jcc.quarkus"

dependencies {
    implementation(project(":bom"))

    implementation(project(":model"))
    implementation(project(":commons"))
    implementation(project(":app"))
    implementation(project(":database:apid"))
    implementation(project(":database:postgresql"))
    implementation(project(":database:redis"))

    //implementation("io.quarkus:quarkus-oidc")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-vertx")
    implementation("io.quarkus:quarkus-vertx-web")
    implementation("io.quarkus:quarkus-scala")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-flyway-deployment")
    implementation("io.quarkus:quarkus-redis-client")

    implementation("org.flywaydb:flyway-core") // { exclude (module: "net.milkbowl:vault:1.2.27") }

    implementation("io.quarkus:quarkus-resteasy-jsonb")
    implementation("org.jboss.logmanager:log4j2-jboss-logmanager")

    implementation("io.smallrye.reactive:mutiny")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-web-client")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-pg-client")

    implementation(libs.elasticsearch)

    implementation(libs.commons.lang)

    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("io.quarkus:quarkus-smallrye-health")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured") {
        exclude("org.codehaus.groovy")
    }
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    testImplementation(libs.bundles.spock)

    if (hasProperty("profile") && property("profile") == "docker") {
        println("Adding dependencies, in order to create a docker image.")
        implementation(platform(libs.quarkus))
        implementation("io.quarkus:quarkus-container-image-docker")
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED"))
}

/**
 * Remove migration SQLs from being packaged in resulting jar
 */
distributions {
    main {
        distributionBaseName.set(project.name)
        contents {
            into("/") {
                from("src/main/resources/db/migration/") {
                    exclude("*.sql")
                }
            }
        }
    }
}
tasks.processResources {
    enabled = true
}

tasks.withType<JavaExec> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
