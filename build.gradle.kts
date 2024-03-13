subprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
    }
}

allprojects {
    group = "org.fish-from-SanDiego"
    version = "1.0-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}