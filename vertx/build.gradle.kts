import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


group = "org.example"
version = "0.1-SNAPSHOT"

val launcherClass = "io.vertx.core.Launcher"

buildscript {
    val shadowVer = "2.0.1"

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:$shadowVer")
    }
}

plugins {
    kotlin("jvm") version "1.2.10"
    application
}

application {
    mainClassName = launcherClass
}

configure<KotlinProjectExtension> {
    experimental.coroutines = Coroutines.ENABLE
}

repositories {
    jcenter()
    mavenCentral()
}

apply {
    plugin("com.github.johnrengelman.shadow")
}

dependencies {
    val vertxVer = "3.5.0"
    val kotlinVer = "1.2.10"

    compile(kotlin(module = "stdlib-jre8", version = kotlinVer))
    compile("io.vertx:vertx-core:$vertxVer")
    compile("io.vertx:vertx-web:$vertxVer")
    compile("io.vertx:vertx-lang-kotlin:$vertxVer")
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "1.8"
}
val run by tasks.getting(JavaExec::class) {
    val mainVerticleName = "org.example.vertx.Server"
    val watchForChange = "src/**/*.kt"
    val watcherAction = "./gradlew classes"

    args(
        "run",
        mainVerticleName,
        "--redeploy=$watchForChange",
        "--launcher-class=$launcherClass",
        "--on-redeploy=$watcherAction"
    )
}
val shadowJar by tasks.getting(ShadowJar::class) {
    baseName = "vertx"
    version = "0.1-SNAPSHOT"
    classifier = ""
}
