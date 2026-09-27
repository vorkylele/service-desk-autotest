package ru.servicedesk.autotest.api.services

import io.qameta.allure.Step
import org.apache.http.HttpStatus.SC_CONFLICT
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.apache.http.HttpStatus.SC_OK
import ru.servicedesk.autotest.api.AbstractApiClient
import ru.servicedesk.autotest.api.Endpoints
import ru.servicedesk.autotest.api.models.ApiError
import ru.servicedesk.autotest.api.models.ApprovalResponse
import ru.servicedesk.autotest.api.models.DecisionRequest
import ru.servicedesk.autotest.api.models.TicketResponse
import ru.servicedesk.autotest.api.parse

class ApprovalClient : AbstractApiClient() {

    @Step("Согласования, ожидающие решения {email}")
    fun pending(email: String): List<ApprovalResponse> = get(asUser(email), Endpoints.APPROVALS, SC_OK).parse()

    @Step("Согласование по заявке {ticketId}, адресованное {email}")
    fun pendingFor(email: String, ticketId: Long): ApprovalResponse = pending(email).first { it.ticketId == ticketId }

    @Step("Решение по согласованию {approvalId} от имени {email}")
    fun decide(email: String, approvalId: Long, decision: DecisionRequest): TicketResponse =
        post(asUser(email), Endpoints.APPROVAL_DECISION, SC_OK, approvalId, body = decision).parse()

    @Step("Решение по согласованию {approvalId} от имени {email} — ожидается 403")
    fun decideForbidden(email: String, approvalId: Long, decision: DecisionRequest) {
        post(asUser(email), Endpoints.APPROVAL_DECISION, SC_FORBIDDEN, approvalId, body = decision)
    }

    @Step("Решение по согласованию {approvalId} от имени {email} — ожидается отказ 409")
    fun decideRejected(email: String, approvalId: Long, decision: DecisionRequest): ApiError =
        post(asUser(email), Endpoints.APPROVAL_DECISION, SC_CONFLICT, approvalId, body = decision).parse()
}
