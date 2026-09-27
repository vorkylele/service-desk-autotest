package ru.servicedesk.autotest.db.services

import io.qameta.allure.Step
import ru.servicedesk.autotest.db.DatabaseClient
import ru.servicedesk.autotest.db.models.GrantRow
import ru.servicedesk.autotest.db.queries.Sql
import java.sql.Timestamp

class AccessGrantQueries {

    @Step("Записи реестра прав работника {email}")
    fun grantsOf(email: String): List<GrantRow> = DatabaseClient.rows(Sql.GRANTS_OF_EMPLOYEE, email).map {
        GrantRow(
            ticketNumber = (it["ticket_number"] as String?)?.trim(),
            resourceCode = (it["resource_code"] as String).trim(),
            roleCode = (it["role_code"] as String).trim(),
            revokedAt = (it["revoked_at"] as Timestamp?)?.toLocalDateTime(),
            revokeReason = it["revoke_reason"] as String?,
        )
    }

    /** Возвращает текст ошибки базы данных либо null, если дубликат неожиданно вставился. */
    @Step("Попытка вставить второе действующее право работника {email}")
    fun tryInsertDuplicateOfActiveGrant(email: String): String? {
        val grant = DatabaseClient.rows(Sql.ACTIVE_GRANT_KEYS, email).single()
        return runCatching {
            DatabaseClient.execute(Sql.INSERT_GRANT, grant["employee_id"], grant["access_role_id"], grant["granted_by"])
        }.exceptionOrNull()?.message
    }
}
