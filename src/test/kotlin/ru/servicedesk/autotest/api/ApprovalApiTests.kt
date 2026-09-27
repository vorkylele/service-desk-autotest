package ru.servicedesk.autotest.api

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.api.assertions.ApprovalAssertions
import ru.servicedesk.autotest.api.assertions.TicketAssertions
import ru.servicedesk.autotest.api.models.builders.ApprovalRequests
import ru.servicedesk.autotest.api.models.builders.TicketRequests
import ru.servicedesk.autotest.config.Users

@Epic("REST API")
@Feature("Согласование")
@Order(2)
class ApprovalApiTests : BaseApiTest() {

    @Test
    @DisplayName("Маршрут для платёжного контура; отказ требует причины и отклоняет заявку")
    fun rejectionRequiresReason() {
        val request = TicketRequests.access("201", "R003", "OPER", "Автотест: доступ оператора", "Проверка отказа")
        val ticket = ticketClient.create(Users.TECH_LEAD, request)
        TicketAssertions.assertOnApproval(ticket)
        val details = ticketClient.details(Users.TECH_LEAD, ticket.id)
        ApprovalAssertions.assertRoute(details, listOf("MANAGER", "OWNER", "SECURITY"), listOf("PENDING", "WAITING", "WAITING"))
        val approval = approvalClient.pendingFor(Users.MANAGER, ticket.id)
        approvalClient.decideForbidden(Users.OWNER, approval.id, ApprovalRequests.approve())
        val error = approvalClient.decideRejected(Users.MANAGER, approval.id, ApprovalRequests.reject())
        ApprovalAssertions.assertReasonRequired(error)
        val rejected = approvalClient.decide(Users.MANAGER, approval.id, ApprovalRequests.reject("Роль оператора тестировщикам и разработчикам не выдаётся"))
        TicketAssertions.assertRejected(rejected)
    }
}
