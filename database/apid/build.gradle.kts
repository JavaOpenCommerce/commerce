plugins {
    idea
}

group = "com.example.jcc.database"

dependencies {
    implementation(project(":model"))
    implementation(project(":commons"))
    implementation(project(":bom"))
    implementation("io.quarkus:quarkus-hibernate-orm")

//    tests to change
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}
