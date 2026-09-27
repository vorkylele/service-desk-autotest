package ru.servicedesk.autotest.api

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.api.models.TicketScope
import ru.servicedesk.autotest.config.Users
import ru.servicedesk.autotest.data.ControlExample

@Epic("REST API")
@Feature("Разграничение прав")
@Order(2)
class AccessControlApiTests : BaseApiTest() {

    @Test
    @DisplayName("Сотруднику недоступны очередь, журнал, отчёты, реестр и кадровые события")
    fun employeeHasNoStaffAccess() {
        ticketClient.listForbidden(Users.APPLICANT, TicketScope.QUEUE)
        ticketClient.listForbidden(Users.APPLICANT, TicketScope.ALL)
        reportClient.slaForbidden(Users.APPLICANT, ControlExample.HISTORY_FROM, ControlExample.HISTORY_TO)
        accessClient.registryForbidden(Users.APPLICANT)
        accessClient.dismissForbidden(Users.APPLICANT, employeeId = 1)
    }

    @Test
    @DisplayName("Исполнитель не может отзывать права и оформлять увольнение")
    fun agentCannotRevokeOrDismiss() {
        accessClient.revokeForbidden(Users.ACCESS_AGENT, grantId = 1, reason = "проверка")
        accessClient.dismissForbidden(Users.ACCESS_AGENT, employeeId = 1)
    }

    @Test
    @DisplayName("Чужая заявка с персональными данными сотруднику недоступна")
    fun foreignTicketIsHidden() {
        val foreignTicket = ticketClient.findByNumber(Users.SUPPORT_HEAD, ControlExample.DEVELOPER_TICKET_NUMBER)
        ticketClient.detailsForbidden(Users.APPLICANT, foreignTicket.id)
    }
}
