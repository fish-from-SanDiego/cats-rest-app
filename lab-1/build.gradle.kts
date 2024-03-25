plugins {
    id("io.freefair.lombok") version ("8.6")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.7.2")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.fishFromSanDiego.lab1.Main";
    }
}

tasks.javadoc {
    source = project.the<SourceSetContainer>()["main"].allJava
}

tasks.test {
    useJUnitPlatform()
}
