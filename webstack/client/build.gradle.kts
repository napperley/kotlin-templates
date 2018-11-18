import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

buildscript {
    var kotlinVer: String by extra

    kotlinVer = "1.3.10"

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

dependencies {
    val kotlinxHtmlVer = "0.6.10"
    "compile"(kotlin(module = "stdlib-js", version = kotlinVer))
    "compile"("org.jetbrains.kotlinx:kotlinx-html-js:$kotlinxHtmlVer")
}

val webDir = "${projectDir.absolutePath}/web"
val compileKotlin2Js by tasks.getting(Kotlin2JsCompile::class) {
    val fileName = "webstack-client.js"

    kotlinOptions.outputFile = "$webDir/js/$fileName"
    kotlinOptions.sourceMap = true
    doFirst { File("$webDir/js").deleteRecursively() }
}
val build by tasks
val assembleWeb by tasks.creating(Copy::class) {
    dependsOn("classes")
    configurations["compile"].forEach { file ->
        from(zipTree(file.absolutePath)) {
            includeEmptyDirs = false
            include { fileTreeElement ->
                val path = fileTreeElement.path
                path.endsWith(".js") && path.startsWith("META-INF/resources/") ||
                    !path.startsWith("META_INF/")
            }
        }
    }
    from(compileKotlin2Js.destinationDir)
    into("$webDir/js")
}

task<Sync>("deployClientToServer") {
    val destDir = "${projectDir.parent}/server/src/main/resources/static"
    dependsOn(compileKotlin2Js, assembleWeb)
    from(webDir)
    into(destDir)
}
