subprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
    }

    dependencies {
        "compileOnly"("org.projectlombok:lombok:1.18.24")
        "annotationProcessor"("org.projectlombok:lombok:1.18.32")
    }
}

allprojects {
    group = "org.fish-from-SanDiego"
    version = "1.0-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}