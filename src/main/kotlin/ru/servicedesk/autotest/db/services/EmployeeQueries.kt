package ru.servicedesk.autotest.db.services

import io.qameta.allure.Step
import ru.servicedesk.autotest.db.DatabaseClient
import ru.servicedesk.autotest.db.models.EmployeeRow
import ru.servicedesk.autotest.db.queries.Sql
import java.sql.Timestamp

class EmployeeQueries {

    @Step("Учётная запись работника {email}")
    fun byEmail(email: String): EmployeeRow = DatabaseClient.rows(Sql.EMPLOYEE_BY_EMAIL, email).single().let {
        EmployeeRow(active = it["active"] as Boolean, dismissedAt = (it["dismissed_at"] as Timestamp?)?.toLocalDateTime())
    }

    @Step("Число учётных записей с паролем не в виде хеша bcrypt")
    fun countPlainPasswords(): Long = DatabaseClient.scalar(Sql.PLAIN_PASSWORDS) as Long
}
