plugins {
    id("com.avast.gradle.docker-compose") version "0.17.6"
}

dependencies {
    implementation(project(":catsDao"))
    implementation(project(":catsCommon"))
    implementation(project(":catsServices"))
    implementation(project(":catsPresentation"))

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    if (this.name.equals("bootRun"))
        dockerCompose.isRequiredBy(this)
}

dockerCompose {
    useComposeFiles.add("./docker-compose.yaml")
    removeVolumes = false
}