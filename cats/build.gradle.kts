plugins {
    id("com.avast.gradle.docker-compose") version "0.17.6"
}

dependencies {
    implementation(project(":catsDao"))
    implementation(project(":catsCommon"))

    implementation(platform("org.hibernate.orm:hibernate-platform:6.4.4.Final"))

    // use the versions from the platform
    implementation("org.hibernate.orm:hibernate-core")
    implementation("jakarta.transaction:jakarta.transaction-api")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    dockerCompose.isRequiredBy(this)
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.fishFromSanDiego.cats.Main"
    }
}

tasks.create<JavaExec>("run") {
    group = "build"
    standardInput = System.`in`
    mainClass.set("org.fishFromSanDiego.cats.Main")
    classpath = sourceSets["main"].runtimeClasspath
}

dockerCompose {
    useComposeFiles.add("./docker-compose.yaml")
    removeVolumes = false
}