dependencies {
    implementation(project(":catsDao"))
    implementation(project(":catsCommon"))
    implementation(project(":catsServices"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}