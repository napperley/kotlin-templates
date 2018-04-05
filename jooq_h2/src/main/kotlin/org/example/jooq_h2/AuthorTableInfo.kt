package org.example.jooq_h2

import org.jooq.Constraint
import org.jooq.Field
import org.jooq.Record
import org.jooq.Table
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.jooq.impl.DSL.constraint

object AuthorTableInfo {
    val table: Table<Record> = DSL.table("AUTHOR")
    /** Unique id field that auto increments. */
    val id: Field<Int> = DSL.field("ID", SQLDataType.INTEGER.nullable(false).identity(true))
    val firstName: Field<String> = DSL.field("FIRST_NAME",
        SQLDataType.VARCHAR(255).defaultValue("null"))
    val lastName: Field<String> = DSL.field("LAST_NAME",
        SQLDataType.VARCHAR(255).defaultValue("null"))
    val primaryKey: Constraint = constraint("PK_AUTHOR").primaryKey(id)
}
