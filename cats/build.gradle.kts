plugins {
    id("com.avast.gradle.docker-compose") version "0.17.6"
//    id("org.springframework.boot") version "3.2.5"
//    id("io.spring.dependency-management") version "1.1.4"
}

dependencies {
//    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    implementation(project(":catsDao"))
    implementation(project(":catsCommon"))
    implementation(project(":catsServices"))
    implementation(project(":catsPresentation"))

    // https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api
    // implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    implementation(platform("org.hibernate.orm:hibernate-platform:6.4.4.Final"))

    // use the versions from the platform
    implementation("org.hibernate.orm:hibernate-core")
    implementation("jakarta.transaction:jakarta.transaction-api")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/com.h2database/h2
    testImplementation("com.h2database:h2:2.2.220")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    if (name.equals("run"))
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