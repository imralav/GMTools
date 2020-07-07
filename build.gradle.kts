version = "1.0"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72" apply(false)
    id("org.jetbrains.kotlin.kapt") version "1.3.72" apply(false)
    id("org.springframework.boot") version "2.3.1.RELEASE" apply(false)
    id("io.spring.dependency-management") version "1.0.9.RELEASE" apply(false)
    id("net.ltgt.apt") version "0.10" apply(false)
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven(url = "https://dl.bintray.com/arrow-kt/arrow-kt/")
    }
}
