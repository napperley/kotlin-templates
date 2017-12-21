import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


group = "org.example"
version = "0.1-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

plugins {
	`maven-publish`
    kotlin(module = "jvm") version "1.2.10"
}

buildscript {
    val dokkaVer = "0.9.14"

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVer")
    }
}

apply {
    plugin("org.jetbrains.dokka")
}

dependencies {
	val kotlinVer = "1.2.10"

    compile(kotlin(module = "stdlib-jre8", version = kotlinVer))
}

publishing {
    publications {
        create("docs", MavenPublication::class.java) {
            from(components["java"])
            artifact(createDokkaJar)
        }
        create("sources", MavenPublication::class.java) {
            from(components["java"])
            artifact(createSourceJar)
        }
    }

    repositories {
        maven { url = uri("$buildDir/repository") }
    }
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "1.8"
}
val dokka by tasks.getting(DokkaTask::class) {
    moduleName = "kotlinhello-lib"
    outputDirectory = "$buildDir/javadoc"
    sourceDirs = files("src/main/kotlin")
    doFirst { File("${projectDir.absolutePath}/build/javadoc").deleteRecursively() }
}
val createDokkaJar = task<Jar>("createDokkaJar") {
    dependsOn(dokka)
    classifier = "javadoc"
    from(dokka.outputDirectory)
}
val createSourceJar = task<Jar>("createSourceJar") {
    dependsOn("classes")
    classifier = "sources"
    from("src/main/kotlin")
}

task("createAllJarFiles") {
    dependsOn("jar", createSourceJar, createDokkaJar)
    println("Creating JAR files (library, sources and documentation)...")
    doLast { println("Finished creating JAR files.") }
}
