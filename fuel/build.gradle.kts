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
    val fuelVer = "1.16.0"
    val coroutinesCoreVer = "1.0.0"

    implementation(kotlin(module = "stdlib-jdk8", version = kotlinVer))
    implementation("com.github.kittinunf.fuel:fuel:$fuelVer")
    implementation("com.github.kittinunf.fuel:fuel-coroutines:$fuelVer")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVer")
}

application {
    mainClassName = "org.example.fuel.MainKt"
}

val run by tasks.getting(JavaExec::class) {
    args("https://www.google.com")
}
