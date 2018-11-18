package org.example.webstack

import kotlinx.html.dom.append
import kotlinx.html.js.b
import kotlin.browser.document

fun main() {
    document.getElementById("main-layout")?.append {
        b { +"Hello World! :)" }
    }
}
