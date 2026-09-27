package ru.servicedesk.autotest.config

/** Параметры стенда: системное свойство → переменная окружения → значение по умолчанию. */
object Config {
    val uiUrl = value("ui.url", "http://localhost:5173")
    val apiUrl = value("api.url", "http://localhost:8080")
    val dbUrl = value("db.url", "jdbc:postgresql://localhost:5433/servicedesk")
    val dbUser = value("db.user", "servicedesk")
    val dbPassword = value("db.password", "servicedesk")
    val browserChannel = value("browser.channel", "")
    val headless = value("headless", "true").toBoolean()

    const val PASSWORD = "demo"

    private fun value(key: String, default: String): String =
        System.getProperty(key) ?: System.getenv(key.uppercase().replace('.', '_')) ?: default
}
