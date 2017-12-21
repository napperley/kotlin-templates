import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile


group = "org.example"
version = "0.1-SNAPSHOT"

plugins {
    `maven-publish`
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

buildscript {
    val dokkaVer = "0.9.14"
    var kotlinVer: String by extra

    kotlinVer = "1.2.10"

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin(module = "gradle-plugin", version = kotlinVer))
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVer")
    }
}

val kotlinVer: String by extra

apply {
    plugin("kotlin2js")
    plugin("org.jetbrains.dokka")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    "compile"(kotlin(module = "stdlib-js", version = kotlinVer))
}

val dokka by tasks.getting(DokkaTask::class) {
    moduleName = "webscene-client"
    outputDirectory = "$buildDir/javadoc"
    sourceDirs = files("src/main/kotlin")
    doFirst { File("${projectDir.absolutePath}/build/javadoc").deleteRecursively() }
}
val compileKotlin2Js by tasks.getting(Kotlin2JsCompile::class) {
    kotlinOptions {
        val fileName = "kotlinhello-lib.js"

        outputFile = "${projectDir.absolutePath}/web/$fileName"
        sourceMap = true
    }
}
val createDokkaJar by tasks.creating(Jar::class) {
    dependsOn(dokka)
    classifier = "javadoc"
    from(dokka.outputDirectory)
}
val createSourceJar by tasks.creating(Jar::class) {
    dependsOn("classes")
    classifier = "sources"
    from("src/main/kotlin")
}

task<Jar>("createAllJarFiles") {
    dependsOn("jar", createSourceJar, createDokkaJar)
    println("Creating JAR files (library, sources and documentation)...")
    doLast { println("Finished creating JAR files.") }
}
