package ru.servicedesk.autotest.api

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.api.assertions.ReportAssertions
import ru.servicedesk.autotest.config.Users
import ru.servicedesk.autotest.data.ControlExample

@Epic("REST API")
@Feature("Отчётность")
@Order(2)
class ReportApiTests : BaseApiTest() {

    @Test
    @DisplayName("Отчёт по нормативам срока за исторический период")
    fun slaReportOnHistory() {
        val report = reportClient.sla(Users.SUPPORT_HEAD, ControlExample.HISTORY_FROM, ControlExample.HISTORY_TO)
        ReportAssertions.assertTotals(
            report, ControlExample.HISTORY_TICKET_TYPES, ControlExample.HISTORY_TICKETS, ControlExample.HISTORY_BREACHED,
        )
    }
}
