package ru.servicedesk.autotest.db.services

import io.qameta.allure.Step
import ru.servicedesk.autotest.db.DatabaseClient
import ru.servicedesk.autotest.db.queries.Sql

class TicketQueries {

    @Step("Решения по шагам согласования заявки № {number}")
    fun approvalDecisions(number: String): List<String> =
        DatabaseClient.rows(Sql.APPROVAL_DECISIONS, number).map { it["decision"] as String }

    @Step("Последовательность статусов заявки № {number} по журналу событий")
    fun statusTrail(number: String): List<String> =
        DatabaseClient.rows(Sql.STATUS_TRAIL, number).map { (it["new_status"] as String).trim() }

    @Step("Признак нарушения срока заявки № {number}")
    fun slaBreached(number: String): Boolean = DatabaseClient.scalar(Sql.SLA_BREACHED, number) as Boolean

    @Step("Число заявок начиная с № {fromNumber} без события регистрации")
    fun countWithoutCreatedEvent(fromNumber: String): Long =
        DatabaseClient.scalar(Sql.TICKETS_WITHOUT_CREATED_EVENT, fromNumber) as Long
}
