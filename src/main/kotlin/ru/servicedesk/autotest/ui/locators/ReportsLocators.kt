package ru.servicedesk.autotest.ui.locators

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.ui.pages.BasePage

open class ReportsLocators(page: Page) : BasePage(page) {
    protected val periodFromInput: Locator = page.locator("input[type=date]").nth(0)
    protected val periodToInput: Locator = page.locator("input[type=date]").nth(1)
    protected val kpiValues: Locator = page.locator(".kpi-value")
}
