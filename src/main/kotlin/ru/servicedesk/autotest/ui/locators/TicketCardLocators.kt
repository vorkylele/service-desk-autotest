package ru.servicedesk.autotest.ui.locators

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.ui.pages.BasePage

open class TicketCardLocators(page: Page) : BasePage(page) {
    protected val title: Locator = page.locator("h1")
    protected val statusBadge: Locator = page.locator("h1 .badge")
    protected val routeSteps: Locator = page.locator(".route li b")
    protected val history: Locator = page.locator(".timeline")
    protected val historyItems: Locator = page.locator(".timeline li")
    protected val resolutionInput: Locator = page.locator(".card textarea").first()
    protected val takeButton: Locator = button("Принять в работу")
    protected val resolveButton: Locator = button("Решить")
    protected val confirmButton: Locator = button("Подтвердить решение")

    protected fun property(label: String): Locator =
        page.locator("dl.props dt", Page.LocatorOptions().setHasText(label)).first()
            .locator("xpath=following-sibling::dd[1]")
}
