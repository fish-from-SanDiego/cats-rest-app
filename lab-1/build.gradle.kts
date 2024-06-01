dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
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
