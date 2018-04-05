package org.example.jooq_h2

import org.jooq.DSLContext

object AuthorRepo {
	fun populate(ctx: DSLContext) {
	    val values1 = mutableListOf("Arther", "Clarke")
	    val values2 = mutableListOf("Jake", "Maw")
	    val fields = arrayOf(AuthorTableInfo.firstName, AuthorTableInfo.lastName)

	    ctx.insertInto(AuthorTableInfo.table, *fields).values(values1).execute()
	    ctx.insertInto(AuthorTableInfo.table, *fields).values(values2).execute()
	}

	fun updateTable(ctx: DSLContext) {
	    ctx
	    	.update(AuthorTableInfo.table)
	        .set(AuthorTableInfo.firstName, "Jackie")
	        .set(AuthorTableInfo.lastName, "Chan")
	        .where(AuthorTableInfo.id.eq(1))
	        .execute()
	}

	fun createTable(ctx: DSLContext) {
	    ctx
	    	.createTableIfNotExists(AuthorTableInfo.table)
	        .column(AuthorTableInfo.id)
	        .column(AuthorTableInfo.firstName)
	        .column(AuthorTableInfo.lastName)
	        .constraints(AuthorTableInfo.primaryKey)
	        .execute()
	}

	fun removeTable(ctx: DSLContext) {
		ctx.dropTable(AuthorTableInfo.table).execute()
	}

	fun allAuthors(ctx: DSLContext) = ctx.select().from(AuthorTableInfo.table).fetch()
}
