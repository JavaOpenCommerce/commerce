plugins {
    `java-library`
}

/**
 * BOM - Bill of materials module was created because Quarkus dependencies leaks into other modules - mutiny is used
 * as a base wrapper class.
 */
dependencies {
    api(platform(libs.quarkus))
    constraints {
        api(libs.flyway)
        api(libs.log4j.api)
        api(libs.log4j.core)

        api(libs.commons.lang)
    }
}
