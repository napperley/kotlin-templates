group = "org.example"
version = "0.1-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

plugins {
    kotlin(module = "jvm") version "1.2.31"
    application
}

application {
    mainClassName = "org.example.jooq_h2.MainKt"
}

dependencies {
    val kotlinVer = "1.2.31"
    val jooqVer = "3.10.6"
    val h2Ver = "1.4.197"

    compile(kotlin(module = "stdlib-jre8", version = kotlinVer))
    compile("org.jooq:jooq:$jooqVer")
    compile("com.h2database:h2:$h2Ver")
}
