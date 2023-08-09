/*
 * The settings file is used to specify which projects to include in your build.
 *
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user guide at https://docs.gradle.org/4.4.1/userguide/multi_project_builds.html
 */

rootProject.name = "jcc"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        `version-catalog`
    }
}
include(
        "bom",
        "app",
        "commons",
        "core",
        "adapters",
        "model",
        "quarkus",
)

//findProject(':database:apid')?.name = 'apid'
//findProject(':database:posgresql')?.name = 'posgresql'

