package ru.servicedesk.autotest.ui.locators

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.ui.pages.BasePage

open class CatalogLocators(page: Page) : BasePage(page) {
    protected val ticketForm: Locator = page.locator("form.form")

    protected fun service(name: String): Locator =
        page.locator("a.service", Page.LocatorOptions().setHasText(name)).first()
}
