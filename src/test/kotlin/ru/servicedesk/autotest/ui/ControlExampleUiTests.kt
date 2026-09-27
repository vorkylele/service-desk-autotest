package ru.servicedesk.autotest.ui

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import ru.servicedesk.autotest.api.models.TicketScope
import ru.servicedesk.autotest.config.Config
import ru.servicedesk.autotest.config.Users
import ru.servicedesk.autotest.data.ControlExample
import ru.servicedesk.autotest.ui.assertions.AccessRegistryAssertions
import ru.servicedesk.autotest.ui.assertions.ApprovalsAssertions
import ru.servicedesk.autotest.ui.assertions.EmployeesAssertions
import ru.servicedesk.autotest.ui.assertions.ReportsAssertions
import ru.servicedesk.autotest.ui.assertions.TicketCardAssertions

@Epic("Контрольный пример")
@Feature("Сквозной сценарий через интерфейс портала")
@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ControlExampleUiTests : BaseUiTest() {
    private lateinit var accessTicketId: String
    private lateinit var incidentTicketId: String
    private lateinit var developerTicketId: String

    @Test
    @Order(1)
    @DisplayName("Заявка на доступ к платёжному контуру: маршрут из трёх шагов и контрольный срок")
    fun accessRequestIsRegisteredWithRoute() {
        val login = portal.loginPage().fillCredentials(Users.APPLICANT, Config.PASSWORD)
        portal.screenshot("01_login")
        login.submit()
        portal.catalog()
        portal.screenshot("02_catalog")
        val form = portal.catalog().choose(ControlExample.ACCESS_SERVICE)
            .fillAccess(ControlExample.PAYMENT_GATEWAY, ControlExample.VIEW_LOGS_ROLE, ControlExample.ACCESS_JUSTIFICATION)
        portal.screenshot("03_new_access_form")
        val card = form.submit()
        accessTicketId = card.ticketId()
        portal.screenshot("04_ticket_on_approval")
        TicketCardAssertions.assertOnApproval(card, ControlExample.ACCESS_TICKET_NUMBER, ControlExample.PAYMENT_GATEWAY_ROUTE)
        TicketCardAssertions.assertDueDateMatchesOracle(card, ControlExample.ACCESS_SLA_MINUTES)
    }

    @Test
    @Order(2)
    @DisplayName("Инцидент маршрутизируется автоматически, без согласования")
    fun incidentIsRoutedAutomatically() {
        val form = portal.catalog().choose(ControlExample.INCIDENT_SERVICE)
            .fillIncident(ControlExample.INCIDENT_SUBJECT, ControlExample.INCIDENT_EQUIPMENT_NO, ControlExample.INCIDENT_DESCRIPTION)
        portal.screenshot("05_new_incident_form")
        val card = form.submit()
        incidentTicketId = card.ticketId()
        portal.screenshot("06_incident_routed")
        TicketCardAssertions.assertRoutedToGroup(card, ControlExample.INCIDENT_TICKET_NUMBER, ControlExample.L1_GROUP)
        TicketCardAssertions.assertDueDateMatchesOracle(card, ControlExample.INCIDENT_SLA_MINUTES)
    }

    @Test
    @Order(3)
    @DisplayName("Вторая заявка на доступ — основа сценария увольнения")
    fun developerRequestsRepositoryAccess() {
        portal.loginAs(Users.DEVELOPER)
        val card = portal.catalog().choose(ControlExample.ACCESS_SERVICE)
            .fillAccess(ControlExample.VCS, ControlExample.DEVELOPER_ROLE, ControlExample.VCS_JUSTIFICATION)
            .submit()
        developerTicketId = card.ticketId()
        TicketCardAssertions.assertRoute(card, ControlExample.VCS_ROUTE)
    }

    @Test
    @Order(4)
    @DisplayName("Согласование: руководитель → владелец ресурса → информационная безопасность")
    fun approvalChainIsPassed() {
        portal.loginAs(Users.MANAGER)
        val approvals = portal.approvals()
        portal.screenshot("07_approvals")
        ApprovalsAssertions.assertPendingCount(approvals, ControlExample.PENDING_APPROVALS_OF_MANAGER)
        approvals.approveFirst(ControlExample.MANAGER_COMMENT)
        approvals.approveFirst()
        portal.loginAs(Users.TECH_LEAD).approvals().approveFirst()
        portal.loginAs(Users.OWNER).approvals().approveFirst(ControlExample.OWNER_COMMENT)
        portal.loginAs(Users.SECURITY).approvals().approveFirst(ControlExample.SECURITY_COMMENT)
        val card = portal.ticketCard(accessTicketId)
        TicketCardAssertions.assertQueuedTo(card, ControlExample.ACCESS_AGENT_NAME)
    }

    @Test
    @Order(5)
    @DisplayName("Исполнитель решает заявки; решение заявки на доступ меняет реестр прав")
    fun agentsResolveTickets() {
        portal.loginAs(Users.ACCESS_AGENT)
        portal.tickets(TicketScope.QUEUE)
        portal.screenshot("08_queue")
        val developerCard = portal.ticketCard(developerTicketId).take().typeResolution(ControlExample.ACCESS_RESOLUTION).resolve()
        TicketCardAssertions.assertStatus(developerCard, "Решена")
        val accessCard = portal.ticketCard(accessTicketId).take().typeResolution(ControlExample.ACCESS_RESOLUTION)
        portal.screenshot("09_resolve")
        accessCard.resolve()
        TicketCardAssertions.assertStatus(accessCard, "Решена")
        portal.loginAs(Users.L1_AGENT)
        val incidentCard = portal.ticketCard(incidentTicketId).take().typeResolution(ControlExample.INCIDENT_RESOLUTION).resolve()
        TicketCardAssertions.assertStatus(incidentCard, "Решена")
    }

    @Test
    @Order(6)
    @DisplayName("Заявитель видит полную историю, подтверждает решение и получает право")
    fun applicantConfirmsAndHasAccess() {
        portal.loginAs(Users.APPLICANT)
        portal.tickets(TicketScope.MY)
        portal.screenshot("10_my_tickets")
        val card = portal.ticketCard(accessTicketId)
        portal.screenshot("11_ticket_history")
        TicketCardAssertions.assertResolvedWithinSla(card, ControlExample.ACCESS_HISTORY_EVENTS)
        card.confirm()
        TicketCardAssertions.assertStatus(card, "Закрыта")
        val registry = portal.accessRegistry()
        portal.screenshot("12_my_access")
        AccessRegistryAssertions.assertFirstRowContains(
            registry, ControlExample.PAYMENT_GATEWAY, ControlExample.VIEW_LOGS_ROLE, "заявка № ${ControlExample.ACCESS_TICKET_NUMBER}",
        )
    }

    @Test
    @Order(7)
    @DisplayName("Повторный запрос действующего права отклоняется")
    fun duplicateRightIsRejected() {
        val form = portal.catalog().choose(ControlExample.ACCESS_SERVICE)
            .fillAccess(ControlExample.PAYMENT_GATEWAY, ControlExample.VIEW_LOGS_ROLE, ControlExample.DUPLICATE_JUSTIFICATION)
        val error = form.submitExpectingError()
        portal.screenshot("13_duplicate_rejected")
        TicketCardAssertions.assertRejectedAsAlreadyGranted(error)
    }

    @Test
    @Order(8)
    @DisplayName("Отчёт о соблюдении нормативов срока")
    fun slaReportShowsTotals() {
        portal.loginAs(Users.SUPPORT_HEAD)
        val reports = portal.reports().setPeriod(ControlExample.HISTORY_FROM, ControlExample.SCENARIO_DATE)
        portal.screenshot("14_reports")
        ReportsAssertions.assertKpi(reports, ControlExample.REPORT_KPI)
    }

    @Test
    @Order(9)
    @DisplayName("Увольнение работника блокирует учётную запись и отзывает права")
    fun dismissalRevokesAllGrants() {
        portal.loginAs(Users.ADMIN)
        val employees = portal.employees()
        portal.screenshot("15_employees")
        val message = employees.dismiss(ControlExample.DISMISSED_EMPLOYEE)
        portal.screenshot("16_dismissed")
        EmployeesAssertions.assertDismissed(message, ControlExample.DISMISSED_EMPLOYEE_GRANTS)
        val registry = portal.accessRegistry().showRevoked()
        portal.screenshot("17_access_registry")
        AccessRegistryAssertions.assertFirstRevokedRowContains(
            registry, ControlExample.DISMISSED_EMPLOYEE_FULL_NAME, ControlExample.DISMISSAL_REASON,
        )
    }
}
