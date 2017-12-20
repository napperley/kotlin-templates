import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile


group = "org.example"
version = "0.1-SNAPSHOT"

buildscript {
    var kotlinVer: String by extra

    kotlinVer = "1.2.10"

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(kotlin(module = "gradle-plugin", version = kotlinVer))
    }
}

val kotlinVer: String by extra

apply {
    plugin("kotlin2js")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    "compile"(kotlin(module = "stdlib-js", version = kotlinVer))
}

val compileKotlin2Js by tasks.getting(Kotlin2JsCompile::class) {
    kotlinOptions {
        val fileName = "kotlinjs-hello.js"

        outputFile = "${projectDir.absolutePath}/web/js/$fileName"
        sourceMap = true
        moduleKind = "umd"
    }
}
val assembleWeb by tasks.creating(Sync::class) {
    dependsOn("classes")
    configurations["compile"].forEach { file ->
        from(zipTree(file.absolutePath)) {
            includeEmptyDirs = false
            include { fileTreeElement ->
                val path = fileTreeElement.path

                path.endsWith(".js") && path.startsWith("META-INF/resources/") || !path.startsWith("META_INF/")
            }
        }
    }
    from(compileKotlin2Js.destinationDir)
    into("${projectDir.absolutePath}/web/js")
}
