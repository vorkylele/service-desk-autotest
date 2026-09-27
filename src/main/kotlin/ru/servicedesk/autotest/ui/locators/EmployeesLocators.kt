package ru.servicedesk.autotest.ui.locators

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.ui.pages.BasePage

open class EmployeesLocators(page: Page) : BasePage(page) {
    protected val infoAlert: Locator = page.locator(".alert.info")

    protected fun dismissButton(employeeName: String): Locator =
        page.locator("tbody tr", Page.LocatorOptions().setHasText(employeeName)).locator("button")
}
