package org.example.fuel

import awaitString
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    println("Contents of ${args.first()}:\n${doHttpGet(args[0])}")
    println("Exiting...")
}

private suspend fun doHttpGet(url: String): String {
    val timeout = 5000
    return url.httpGet().apply { timeout(timeout) }.awaitString()
}
