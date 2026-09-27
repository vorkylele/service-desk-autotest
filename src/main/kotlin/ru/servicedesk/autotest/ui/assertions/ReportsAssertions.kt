package ru.servicedesk.autotest.ui.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.ui.pages.ReportsPage

object ReportsAssertions {

    @Step("Проверка: показатели отчёта {expected}")
    fun assertKpi(page: ReportsPage, expected: List<String>) {
        assertThat(page.kpi()).containsExactlyElementsOf(expected)
    }
}
