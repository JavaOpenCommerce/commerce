plugins {
    `java-library`
}

/**
 * BOM - Bill of materials module was created because Quarkus dependencies leaks into other modules - mutiny is used
 * as a base wrapper class.
 */
dependencies {
    api(platform(libs.quarkus))
    api(platform(libs.spock.bom))
    constraints {
        api(libs.flyway)
        api(libs.log4j.api)
        api(libs.log4j.core)

        api(libs.commons.lang)
        api(libs.spock)
        api("org.spockframework:spock-core")
        // optional dependencies for using Spock
        api("org.hamcrest:hamcrest-core")   // only necessary if Hamcrest matchers are used
        api("net.bytebuddy:byte-buddy")     // allows mocking of classes (in addition to interfaces)
        api("org.objenesis:objenesis")      // allows mocking of classes without default constructor (together with ByteBuddy or CGLIB)
    }
}
