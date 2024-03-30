plugins {
    id("com.avast.gradle.docker-compose") version "0.17.6"
}

dependencies {
    implementation(project(":catsDao"))
    implementation(project(":catsCommon"))
    implementation(project(":catsServices"))

    // https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/com.h2database/h2
    testImplementation("com.h2database:h2:2.2.220")

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