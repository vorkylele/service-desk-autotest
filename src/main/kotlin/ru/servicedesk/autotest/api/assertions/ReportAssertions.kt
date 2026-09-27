package ru.servicedesk.autotest.api.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.api.models.SlaReportRow

object ReportAssertions {

    @Step("Проверка: {rows} видов заявок, всего {total}, с нарушением срока {breached}")
    fun assertTotals(report: List<SlaReportRow>, rows: Int, total: Int, breached: Int) {
        assertThat(report).hasSize(rows)
        assertThat(report.sumOf { it.total }).isEqualTo(total)
        assertThat(report.sumOf { it.breached }).isEqualTo(breached)
    }
}
