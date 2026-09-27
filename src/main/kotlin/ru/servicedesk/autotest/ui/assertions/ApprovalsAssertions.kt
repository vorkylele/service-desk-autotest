package ru.servicedesk.autotest.ui.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.ui.pages.ApprovalsPage

object ApprovalsAssertions {

    @Step("Проверка: на согласовании {expected} заявок")
    fun assertPendingCount(page: ApprovalsPage, expected: Int) {
        assertThat(page.pendingCount()).isEqualTo(expected)
    }
}
