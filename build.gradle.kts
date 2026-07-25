plugins {
    java
    id("com.gradleup.shadow") version "8.3.9"
}

group = "sysc3110"
version = "5.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // JSON parsing
    implementation("com.google.code.gson:gson:2.13.2")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.25.2")
    implementation("org.apache.logging.log4j:log4j-api:2.26.1")
    implementation("org.apache.logging.log4j:log4j-core:2.25.2")

    // UI theming
    implementation("com.jtattoo:JTattoo:1.6.13")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-engine:6.0.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok:1.18.46")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.46")
}

tasks.test {
    useJUnitPlatform()
}

// Configure Shadow plugin to create fat JAR
tasks.shadowJar {
    archiveFileName.set("Rabbits-and-Foxes.jar")
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "ui.MainMenu"
    }
}

// Make build task depend on shadowJar instead of regular jar
tasks.build {
    dependsOn(tasks.shadowJar)
}

// Disable the regular jar task (similar to Maven's skip configuration)
tasks.jar {
    enabled = false
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
