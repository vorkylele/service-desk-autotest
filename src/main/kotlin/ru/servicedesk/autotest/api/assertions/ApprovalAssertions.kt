package ru.servicedesk.autotest.api.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.api.models.ApiError
import ru.servicedesk.autotest.api.models.TicketDetailsResponse

object ApprovalAssertions {

    @Step("Проверка: маршрут согласования {kinds} с решениями {decisions}")
    fun assertRoute(details: TicketDetailsResponse, kinds: List<String>, decisions: List<String>) {
        assertThat(details.approvals.map { it.kind }).containsExactlyElementsOf(kinds)
        assertThat(details.approvals.map { it.decision }).containsExactlyElementsOf(decisions)
    }

    @Step("Проверка: отказ без причины не принимается")
    fun assertReasonRequired(error: ApiError) {
        assertThat(error.message).contains("указать причину")
    }
}
