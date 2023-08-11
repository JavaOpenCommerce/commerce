@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.quarkus)
    distribution
}
group = "com.example.jcc.infrastructure"

dependencies {
    implementation(project(":bom"))

    implementation(project(":model"))
    implementation(project(":commons"))
    implementation(project(":app"))
    implementation(project(":adapters"))

    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-vertx")
    implementation("io.quarkus:quarkus-vertx-web")
    implementation("io.quarkus:quarkus-scala")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-flyway-deployment")
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-redis-client")

    implementation("io.quarkus:quarkus-resteasy-jsonb")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-web-client")
    implementation(libs.jackson.databind)

    implementation(libs.elasticsearch)

    implementation(libs.commons.lang)

    implementation(libs.log4j.api)
    implementation(libs.log4j.core)

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured") {
        exclude("org.codehaus.groovy")
    }
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    testImplementation(libs.bundles.spock)
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
