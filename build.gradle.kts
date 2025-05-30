plugins {
    id("java")
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

allprojects {
    group = "org.fish-from-SanDiego"
    version = "1.0.0-SNAPSHOT"
    repositories {
        mavenCentral()
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    val currentProject = this
    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }
    dependencies {
        if (currentProject.name == "cats") {
            currentProject.tasks.bootJar {
                enabled = true
            }
        } else {
            currentProject.tasks.bootJar {
                enabled = false
            }
        }
        currentProject.tasks.jar {
            enabled = true
        }
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-security")
        compileOnly("org.projectlombok:lombok")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.security:spring-security-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
}
tasks.bootJar {
    enabled = false
}
tasks.bootRun {
    enabled = false
}
tasks.bootTestRun {
    enabled = false
}
tasks.jar {
    enabled = false
}

tasks.register("checkSubProjectNames") {
    group = "checkInfo"
    for (pr in subprojects) {
        println(pr.name)
    }
}

