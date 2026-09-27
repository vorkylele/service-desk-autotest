package ru.servicedesk.autotest.api.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.api.models.ApiError
import ru.servicedesk.autotest.api.models.TicketResponse
import ru.servicedesk.autotest.data.TicketStatus
import ru.servicedesk.autotest.utils.SlaOracle

object TicketAssertions {

    @Step("Проверка: заявка в очереди, исполнитель назначен автоматически")
    fun assertQueuedWithAssignee(ticket: TicketResponse) {
        assertThat(ticket.statusCode).isEqualTo(TicketStatus.QUEUED)
        assertThat(ticket.assignee).isNotNull()
    }

    @Step("Проверка: контрольный срок совпадает с эталоном при нормативе {normMinutes} мин")
    fun assertDueDateMatchesOracle(ticket: TicketResponse, normMinutes: Int) {
        assertThat(ticket.dueAt).isEqualTo(SlaOracle.dueAt(ticket.createdAt, normMinutes))
    }

    @Step("Проверка: заявка направлена на согласование")
    fun assertOnApproval(ticket: TicketResponse) {
        assertThat(ticket.statusCode).isEqualTo(TicketStatus.ON_APPROVAL)
    }

    @Step("Проверка: заявка отклонена")
    fun assertRejected(ticket: TicketResponse) {
        assertThat(ticket.statusCode).isEqualTo(TicketStatus.REJECTED)
    }

    @Step("Проверка: заявка в работе")
    fun assertInProgress(ticket: TicketResponse) {
        assertThat(ticket.statusCode).isEqualTo(TicketStatus.IN_PROGRESS)
    }

    @Step("Проверка: отказ — право уже предоставлено")
    fun assertAlreadyGranted(error: ApiError) {
        assertThat(error.message).contains("уже предоставлено")
    }

    @Step("Проверка: отказ — отзывать нечего")
    fun assertNothingToRevoke(error: ApiError) {
        assertThat(error.message).contains("отзывать нечего")
    }
}
