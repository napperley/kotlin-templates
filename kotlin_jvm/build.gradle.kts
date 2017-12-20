group = "org.example"
version = "0.1-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

plugins {
    kotlin(module = "jvm") version "1.2.10"
    application
}

application {
    mainClassName = "org.example.kotlinhello.MainKt"
}

dependencies {
	val kotlinVer = "1.2.10"

    compile(kotlin(module = "stdlib-jre8", version = kotlinVer))
}
