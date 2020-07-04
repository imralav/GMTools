version = "1.0"

plugins {
    java
    application
    groovy
    idea
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("net.ltgt.apt") version "0.10"
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "com.imralav.gmtools.App"
}

dependencies {
    implementation("com.google.guava:guava:23.0")
    implementation("org.apache.commons:commons-lang3:3.7")
    implementation("org.apache.commons:commons-collections4:4.2")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")

    implementation("org.slf4j:slf4j-api:1.7.2")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")

    implementation("org.springframework.boot:spring-boot-starter")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.projectlombok:lombok:1.18.4")
    annotationProcessor("org.projectlombok:lombok:1.18.4")

    testImplementation("org.codehaus.groovy:groovy-all:2.4.13")
    testImplementation("org.spockframework:spock-core:1.0-groovy-2.4")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.72")
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xinline-classes")
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
