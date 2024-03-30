dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":catsCommon"))
    implementation(platform("org.hibernate.orm:hibernate-platform:6.4.4.Final"))

    // use the versions from the platform
    implementation("org.hibernate.orm:hibernate-core")
    implementation("jakarta.transaction:jakarta.transaction-api")

    implementation("org.postgresql:postgresql:42.6.2")

}

tasks.test {
    useJUnitPlatform()
}