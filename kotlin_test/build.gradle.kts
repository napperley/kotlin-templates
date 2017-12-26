group = "org.example"
version = "0.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.2.10"
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    val kotlinVer = "1.2.10"
    val kotlinTestVer = "2.0.7"

    compile(kotlin(module = "stdlib-jre8", version = kotlinVer))
    testCompile("io.kotlintest:kotlintest:$kotlinTestVer")
}
