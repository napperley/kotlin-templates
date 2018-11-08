@file:Suppress("PackageName")

package org.example.ktor_server

import io.ktor.application.call
import io.ktor.html.respondHtml
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
        routing {
            baseRoute(this)
        }
    }.start(wait = true)
}

fun baseRoute(routing: Routing) = routing.get("/") {
    call.respondHtml {
        head { title("Ktor Server") }
        body {
            h1 { +"Home Page" }
        }
    }
}
