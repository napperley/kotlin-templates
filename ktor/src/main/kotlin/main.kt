@file:Suppress("PackageName")

package org.example.ktor_server

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
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
        install(StatusPages, setupStatusPages())
        routing {
            baseRoute(this)
            helloRoute(this)
        }
    }.start(wait = true)
}

private fun setupStatusPages(): StatusPages.Configuration.() -> Unit = {
    status(HttpStatusCode.NotFound) { call.respondText("Cannot find ${call.request.path()}") }
    status(HttpStatusCode.InternalServerError) { call.respondText("Server is malfunctioning! :(") }
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