dependencies {
    implementation(project(":bom"))

    implementation("jakarta.json.bind:jakarta.json.bind-api")

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}
