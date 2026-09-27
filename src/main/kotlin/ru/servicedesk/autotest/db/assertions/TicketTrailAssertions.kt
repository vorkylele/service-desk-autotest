package ru.servicedesk.autotest.db.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.data.TicketStatus

object TicketTrailAssertions {

    @Step("Проверка: все {steps} шагов согласованы")
    fun assertAllApproved(decisions: List<String>, steps: Int) {
        assertThat(decisions).hasSize(steps).containsOnly("APPROVED")
    }

    @Step("Проверка: полный жизненный цикл заявки на доступ в журнале событий")
    fun assertFullAccessTrail(statuses: List<String>) {
        assertThat(statuses).containsExactly(
            TicketStatus.QUEUED, TicketStatus.ON_APPROVAL, TicketStatus.QUEUED,
            TicketStatus.IN_PROGRESS, TicketStatus.RESOLVED, TicketStatus.CLOSED,
        )
    }

    @Step("Проверка: заявка решена без нарушения срока")
    fun assertWithinSla(breached: Boolean) {
        assertThat(breached).isFalse()
    }

    @Step("Проверка: заявок без события регистрации нет")
    fun assertNoTicketsWithoutCreatedEvent(count: Long) {
        assertThat(count).isZero()
    }
}
