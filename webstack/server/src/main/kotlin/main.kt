package org.example.webstack

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*

private const val STATIC_DIR = "/static"

fun main() {
    embeddedServer(factory = Netty, port = 8080) {
        install(ContentNegotiation, setupJackson())
        install(StatusPages, setupStatusPages())
        routing {
            static(STATIC_DIR, setupStaticRoutes())
            baseRoute(this)
            helloRoute(this)
        }
    }.start(wait = true)
}

private fun setupStaticRoutes(): Route.() -> Unit = {
    resources("static.js")
}

private fun setupStatusPages(): StatusPages.Configuration.() -> Unit = {
    status(HttpStatusCode.NotFound) { call.respondText("Cannot find ${call.request.path()}") }
    status(HttpStatusCode.InternalServerError) { call.respondText("Server is malfunctioning! :(") }
}

fun helloRoute(routing: Routing) = routing.get("/hello") {
    call.respond(mapOf("msg" to "Hello World! :)"))
}

fun baseRoute(routing: Routing) = routing.get("/") {
    val scriptSources = listOf(
        "$STATIC_DIR/kotlin.js",
        "$STATIC_DIR/webstack-client.js"
    )
    call.respondHtml {
        head { title("Repo Stats") }
        body {
            div { id = "main-layout" }
            scriptSources.forEach { src -> script(block = createJsScript(src)) }
        }
    }
}

private fun setupJackson(): ContentNegotiation.Configuration.() -> Unit = {
    jackson {
        enable(SerializationFeature.INDENT_OUTPUT)
    }
}

private fun createJsScript(newSrc: String): SCRIPT.() -> Unit = {
    type = "text/javascript"
    src = newSrc
}
