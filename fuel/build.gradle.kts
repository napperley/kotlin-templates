group = "org.example"
version = "0.1-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.3.0"
    application
}

dependencies {
    val kotlinVer = "1.3.0"
    implementation(kotlin(module = "stdlib-jdk8", version = kotlinVer))
}

application {
    mainClassName = "org.example.fuel.MainKt"
}
