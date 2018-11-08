@file:Suppress("PackageName")

package org.example.ktor_server

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.title

fun main() {
    embeddedServer(factory = Netty, port = 8080) {
        install(ContentNegotiation, setupJackson())
        routing {
            baseRoute(this)
            helloRoute(this)
        }
    }.start(wait = true)
}

fun helloRoute(routing: Routing) = routing.get("/hello") {
    call.respond(mapOf("msg" to "Hello World! :)"))
}

fun baseRoute(routing: Routing) = routing.get("/") {
    call.respondHtml {
        head { title("Ktor Server") }
        body {
            h1 { +"Home Page" }
        }
    }
}

private fun setupJackson(): ContentNegotiation.Configuration.() -> Unit = {
    jackson {
        enable(SerializationFeature.INDENT_OUTPUT)
    }
}