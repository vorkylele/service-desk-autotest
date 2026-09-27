package ru.servicedesk.autotest.db

import ru.servicedesk.autotest.config.Config
import java.sql.Connection
import java.sql.DriverManager

/** Прямой доступ к базе данных стенда в обход приложения. */
object DatabaseClient {

    fun rows(sql: String, vararg args: Any?): List<Map<String, Any?>> = connection { c ->
        c.prepareStatement(sql).use { st ->
            args.forEachIndexed { i, a -> st.setObject(i + 1, a) }
            st.executeQuery().use { rs ->
                val meta = rs.metaData
                generateSequence {
                    if (rs.next()) (1..meta.columnCount).associate { meta.getColumnLabel(it) to rs.getObject(it) } else null
                }.toList()
            }
        }
    }

    fun scalar(sql: String, vararg args: Any?): Any? = rows(sql, *args).single().values.single()

    fun execute(sql: String, vararg args: Any?): Int = connection { c ->
        c.prepareStatement(sql).use { st ->
            args.forEachIndexed { i, a -> st.setObject(i + 1, a) }
            st.executeUpdate()
        }
    }

    private fun <T> connection(block: (Connection) -> T): T =
        DriverManager.getConnection(Config.dbUrl, Config.dbUser, Config.dbPassword).use(block)
}
