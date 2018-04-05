package org.example.jooq_h2

import org.h2.Driver
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection
import java.sql.DriverManager

import kotlin.system.exitProcess


fun main(args: Array<String>) {
	val db_name = "library"
	val db_location = "~/repos/kotlin-templates/jooq_h2/src/main/resources/db"
    val url = "jdbc:h2:$db_location/$db_name"
    val conn: Connection?

    println("Connecting to DB...")
    Class.forName(Driver::class.java.name).newInstance()
    conn = DriverManager.getConnection(url)
    if (conn != null) {
        DSL.using(conn, SQLDialect.H2).use { ctx ->
            AuthorRepo.createTable(ctx)
            AuthorRepo.populate(ctx)
            AuthorRepo.updateTable(ctx)
            printAuthors(ctx)
            AuthorRepo.removeTable(ctx)
        }
    } else {
    	println("Cannot connect to DB!")
    	exitProcess(-1)
    }
    println("Exiting...")
}

private fun printAuthors(ctx: DSLContext) {
	println("-- Authors --")
    AuthorRepo.allAuthors(ctx).forEach { author ->
        println("Name: ${author[AuthorTableInfo.firstName]} ${author[AuthorTableInfo.lastName]}")
    }
}
