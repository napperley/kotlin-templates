package org.example.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.kotlin.core.http.HttpServerOptions
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

/**
 * Main Verticle that runs the web server.
 */
@Suppress("unused")
class Server : AbstractVerticle() {
    override fun start() {
        val router = Router.router(vertx)
        val serverOptions = HttpServerOptions(port = 8080, host = "localhost", ssl = false)

        println("Starting server...")
        setupRoutes(router)
        // Must assign a request handler to the HTTP server otherwise an exception is thrown.
        vertx.createHttpServer(serverOptions).requestHandler { router.accept(it) }.listen()
        println("Server Access: http://${serverOptions.host}:${serverOptions.port}")
    }

    override fun stop() {
        println("Stopping server...")
    }

    private fun setupRoutes(router: Router) {
        router.route("/public/*").handler(StaticHandler.create("public"))
        router.route("/").handler(homeRoute())
        router.route("/hello").handler(helloRoute())
    }

    private fun helloRoute() = Handler { routingCtx: RoutingContext ->
        val resp = routingCtx.response()
        val jsonStr = json { obj("hello" to "Hello World! :)") }.toString()

        resp.statusCode = Status.OK.num
        resp.putHeader("content-type", ContentType.JSON.txt).end(jsonStr)
    }

    private fun homeRoute() = Handler { routingCtx: RoutingContext ->
        val resp = routingCtx.response()
        val html = """
            <!DOCTYPE html>
            <html>
                <head>
                    <title>Vert.x Server</title>
                <head>
                    <h1>Home Page</h1>
                <body>
                </body>
            </html>
        """

        resp.statusCode = Status.OK.num
        resp.putHeader("content-type", ContentType.HTML.txt).end(html)
    }
}
