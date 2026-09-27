package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import ru.servicedesk.autotest.ui.locators.ReportsLocators
import java.time.LocalDate

class ReportsPage(page: Page) : ReportsLocators(page) {

    @Step("Открыть отчёты")
    fun open() = apply { navigate("/reports") }

    @Step("Задать период {from} — {to}")
    fun setPeriod(from: LocalDate, to: LocalDate) = apply {
        periodFromInput.fill(from.toString())
        periodToInput.fill(to.toString())
        page.waitForTimeout(800.0)
    }

    fun kpi(): List<String> = kpiValues.allInnerTexts()
}
