package org.example.vertx

enum class Status(val num: Int) {
    OK(200), NOT_FOUND(404), SERVER_ERROR(500)
}
