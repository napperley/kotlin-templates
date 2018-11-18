import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
    maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
}

plugins {
    kotlin(module = "jvm") version "1.3.10"
    application
}

application {
    mainClassName = "org.example.webstack.MainKt"
}

dependencies {
    val kotlinVer = "1.3.10"
    val ktorVer = "1.0.0-rc"
    val log4jVer = "1.7.25"
    implementation(kotlin(module = "stdlib-jdk8", version = kotlinVer))
    implementation("io.ktor:ktor-html-builder:$ktorVer")
    implementation("io.ktor:ktor-server-netty:$ktorVer")
    implementation("org.slf4j:slf4j-log4j12:$log4jVer")
    implementation("io.ktor:ktor-html-builder:$ktorVer")
    implementation("io.ktor:ktor-jackson:$ktorVer")
}

val run by tasks.getting {
    doFirst { println("Starting server...") }
    doLast { println("Server ready.") }
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.jvmTarget = "1.8"
}
