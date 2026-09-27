package ru.servicedesk.autotest.ui.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.ui.pages.TicketCardPage
import ru.servicedesk.autotest.utils.SlaOracle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TicketCardAssertions {
    private val SCREEN_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")

    @Step("Проверка: заявка № {number} на согласовании, маршрут {route}")
    fun assertOnApproval(card: TicketCardPage, number: String, route: List<String>) {
        assertThat(card.number()).isEqualTo(number)
        assertThat(card.status()).isEqualTo("На согласовании")
        assertThat(card.routeSteps()).containsExactlyElementsOf(route)
    }

    @Step("Проверка: маршрут согласования {route}")
    fun assertRoute(card: TicketCardPage, route: List<String>) {
        assertThat(card.routeSteps()).containsExactlyElementsOf(route)
    }

    @Step("Проверка: инцидент № {number} в очереди группы «{group}», исполнитель назначен")
    fun assertRoutedToGroup(card: TicketCardPage, number: String, group: String) {
        assertThat(card.number()).isEqualTo(number)
        assertThat(card.status()).isEqualTo("В очереди")
        assertThat(card.supportGroup()).isEqualTo(group)
        assertThat(card.assignee()).isNotEqualTo("не назначен")
    }

    @Step("Проверка: контрольный срок соответствует эталону при нормативе {normMinutes} мин")
    fun assertDueDateMatchesOracle(card: TicketCardPage, normMinutes: Int) {
        val createdAt = LocalDateTime.parse(card.createdAt(), SCREEN_FORMAT)
        assertThat(card.dueAt()).startsWith(SlaOracle.dueAt(createdAt, normMinutes).format(SCREEN_FORMAT))
    }

    @Step("Проверка: заявка в очереди, исполнитель {assignee}")
    fun assertQueuedTo(card: TicketCardPage, assignee: String) {
        assertThat(card.status()).isEqualTo("В очереди")
        assertThat(card.assignee()).isEqualTo(assignee)
    }

    @Step("Проверка: статус заявки «{status}»")
    fun assertStatus(card: TicketCardPage, status: String) {
        assertThat(card.status()).isEqualTo(status)
    }

    @Step("Проверка: история из {events} событий завершается решением в срок")
    fun assertResolvedWithinSla(card: TicketCardPage, events: Int) {
        assertThat(card.history()).hasSize(events)
        assertThat(card.history().last()).contains("Решена в пределах контрольного срока")
    }

    @Step("Проверка: отказ в регистрации — право уже предоставлено")
    fun assertRejectedAsAlreadyGranted(error: String) {
        assertThat(error).contains("уже предоставлено")
    }
}
