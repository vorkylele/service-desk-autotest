package ru.servicedesk.autotest.api

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.api.assertions.TicketAssertions
import ru.servicedesk.autotest.api.models.builders.TicketRequests
import ru.servicedesk.autotest.config.Users

@Epic("REST API")
@Feature("Заявки")
@Order(2)
class TicketApiTests : BaseApiTest() {

    @Test
    @Story("Расчёт контрольного срока")
    @DisplayName("Срок инцидента: 8 рабочих часов с переходом на следующий день")
    fun incidentDueDateIsCalculatedInWorkingTime() {
        val request = TicketRequests.incident("101", "Автотест: не работает гарнитура", "Проверка расчёта контрольного срока")
        val ticket = ticketClient.create(Users.TECH_LEAD, request)
        TicketAssertions.assertQueuedWithAssignee(ticket)
        TicketAssertions.assertDueDateMatchesOracle(ticket, normMinutes = 8 * 60)
    }

    @Test
    @Story("Расчёт контрольного срока")
    @DisplayName("Критический приоритет сокращает норматив вчетверо")
    fun criticalPriorityShortensSla() {
        val request = TicketRequests.incident("102", "Автотест: недоступен стенд", "Проверка приоритета", priorityCode = 1)
        val ticket = ticketClient.create(Users.TECH_LEAD, request)
        TicketAssertions.assertDueDateMatchesOracle(ticket, normMinutes = 60)
    }

    @Test
    @Story("Бизнес-правила")
    @DisplayName("Нельзя запросить действующее право и отозвать отсутствующее")
    fun accessPreconditionsAreChecked() {
        val duplicate = TicketRequests.access("201", "R003", "VIEW", "дубль", "дубль")
        val revokeAbsent = TicketRequests.access("203", "R003", "VIEW", "отзыв", "отзыв")
        TicketAssertions.assertAlreadyGranted(ticketClient.createRejected(Users.APPLICANT, duplicate))
        TicketAssertions.assertNothingToRevoke(ticketClient.createRejected(Users.TECH_LEAD, revokeAbsent))
    }

    @Test
    @Story("Бизнес-правила")
    @DisplayName("Исполнитель чужой группы не может принять заявку в работу")
    fun agentOfAnotherGroupCannotTake() {
        val request = TicketRequests.incident("101", "Автотест: чужая группа", "Проверка групп")
        val ticket = ticketClient.create(Users.TECH_LEAD, request)
        ticketClient.takeForbidden(Users.ACCESS_AGENT, ticket.id)
        val taken = ticketClient.take(Users.L1_AGENT, ticket.id)
        TicketAssertions.assertInProgress(taken)
    }
}
