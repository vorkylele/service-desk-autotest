package ru.servicedesk.autotest.ui.locators

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.ui.pages.BasePage

open class AccessRegistryLocators(page: Page) : BasePage(page) {
    protected val onlyActiveCheckbox: Locator = page.locator(".check input")
    protected val rows: Locator = page.locator("tbody tr")
    protected val revokedRows: Locator = page.locator("tbody tr.revoked")
}
