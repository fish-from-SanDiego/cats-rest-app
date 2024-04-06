dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.fishFromSanDiego.lab1.Main"
    }
}

tasks.create<JavaExec>("run") {
    group = "build"
    standardInput = System.`in`
    mainClass.set("org.fishFromSanDiego.lab1.Main")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.javadoc {
    source = project.the<SourceSetContainer>()["main"].allJava
}

tasks.test {
    useJUnitPlatform()
}
