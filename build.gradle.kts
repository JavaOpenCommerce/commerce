//import org.asciidoctor.gradle.jvm.AsciidoctorTask
import java.nio.charset.StandardCharsets

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    java
    idea
    `java-library`
    alias(libs.plugins.lombok)
//    alias(libs.plugins.asciidoctor.convert)
//    alias(libs.plugins.asciidoctor.gems)
//    alias(libs.plugins.asciidoctor.pdf)
    alias(libs.plugins.spotless)
}

// In this section you declare where to find the dependencies of your project
repositories {
    mavenLocal()
    mavenCentral()
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    version = "0.0.5-SNAPSHOT"

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
        }
    }
    tasks.withType<JavaCompile> {
        options.encoding = StandardCharsets.UTF_8.toString()
    }
    tasks.test {
        useJUnitPlatform()
        testLogging.showExceptions = true
        testLogging {
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
    }
}

// ascii docs, gems is using snakeyaml org.yaml:snakeyaml:1.23. For security reasons it's commented out for now.
//tasks {
//    withType<AsciidoctorTask> {
//        sourceDir {
//            setBaseDir(file("docs"))
//        }
//        setOutputDir(file("build/docs"))
//        baseDirFollowsSourceFile()
//
//        attributes(
//                mapOf(
//                        "build-gradle" to file("build.gradle"),
//                        "endpoint-url" to "http://example.org",
//                        "source-highlighter" to "coderay",
//                        "coderay-css" to "style",
//                        "imagesdir" to "images",
//                        "toc" to "left",
//                        "icons" to "font",
//                        "setanchors" to "",
//                        "idprefix" to "",
//                        "idseparator" to "-",
//                        "docinfo" to "shared"
//                )
//        )
//
//        dependsOn("asciidoctorj")
//    }
//}
//
//asciidoctorj {
//    modules {
//        diagram.use()
//        diagram.setVersion("1.5.16")
//    }
//}
/**
 * OpenJDK 9/10 Mac issue:
 * Caused by: java.lang.UnsatisfiedLinkError: /path/to/openjdk10/lib/libfontmanager.dylib: dlopen(/path/to/openjdk10/lib/libfontmanager.dylib, 1): Library not loaded: /Users/jenkins/workspace/openjdk10_build_x86-64_macos/openjdk/installedfreetype/lib/libfreetype.6.dylib
 *   Referenced from: /path/to/openjdk10/lib/libfontmanager.dylib
 *   Reason: image not found
 * The solution is to install freetype via HomeBrew or MacPorts. You might also need to do something (ridiculous) such as
 *
 * $ sudo mkdir -p /Users/jenkins/workspace/openjdk10_build_x86-64_macos/openjdk/installedfreetype
 * $ sudo ln -s /opt/local/lib /Users/jenkins/workspace/openjdk10_build_x86-64_macos/openjdk/installedfreetype/lib
 */
