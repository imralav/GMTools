import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

version = "1.0"

plugins {
    java
    application
    groovy
    idea
    id("org.jetbrains.kotlin.jvm")
//    id("org.jetbrains.kotlin.kapt")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("net.ltgt.apt")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClassName = "com.imralav.gmtools.gui.App"
}

val arrow_version = "0.10.4"
dependencies {
    implementation(project(":domain"))

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
    implementation("io.arrow-kt:arrow-core:$arrow_version")
    implementation("io.arrow-kt:arrow-syntax:$arrow_version")
//    kapt("io.arrow-kt:arrow-meta:$arrow_version")
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
