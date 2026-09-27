package ru.servicedesk.autotest.db.queries

object Sql {

    val GRANTS_OF_EMPLOYEE = """
        SELECT t.number AS ticket_number, r.code AS resource_code, ar.code AS role_code, g.revoked_at, g.revoke_reason
        FROM access_grant g
             JOIN employee e ON e.id = g.employee_id
             JOIN access_role ar ON ar.id = g.access_role_id
             JOIN info_resource r ON r.id = ar.resource_id
             LEFT JOIN ticket t ON t.id = g.ticket_id
        WHERE e.email = ?
        ORDER BY g.id
    """.trimIndent()

    val ACTIVE_GRANT_KEYS = """
        SELECT g.employee_id, g.access_role_id, g.granted_by
        FROM access_grant g JOIN employee e ON e.id = g.employee_id
        WHERE e.email = ? AND g.revoked_at IS NULL
    """.trimIndent()

    const val INSERT_GRANT =
        "INSERT INTO access_grant (employee_id, access_role_id, granted_at, granted_by) VALUES (?, ?, now(), ?)"

    const val EMPLOYEE_BY_EMAIL = "SELECT active, dismissed_at FROM employee WHERE email = ?"

    val PLAIN_PASSWORDS = "SELECT count(*) FROM employee WHERE password_hash IS NULL OR password_hash NOT LIKE '${'$'}2a${'$'}10${'$'}%'"

    val APPROVAL_DECISIONS = """
        SELECT a.decision FROM approval a JOIN ticket t ON t.id = a.ticket_id
        WHERE t.number = ? ORDER BY a.step_no
    """.trimIndent()

    val STATUS_TRAIL = """
        SELECT e.new_status FROM ticket_event e JOIN ticket t ON t.id = e.ticket_id
        WHERE t.number = ? AND e.new_status IS NOT NULL ORDER BY e.id
    """.trimIndent()

    const val SLA_BREACHED = "SELECT sla_breached FROM ticket WHERE number = ?"

    val TICKETS_WITHOUT_CREATED_EVENT = """
        SELECT count(*) FROM ticket t
        WHERE t.number >= ?
          AND NOT EXISTS (SELECT 1 FROM ticket_event e WHERE e.ticket_id = t.id AND e.event_type = 'CREATED')
    """.trimIndent()
}
