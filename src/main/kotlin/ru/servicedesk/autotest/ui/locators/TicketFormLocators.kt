package ru.servicedesk.autotest.ui.locators

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.ui.pages.BasePage

open class TicketFormLocators(page: Page) : BasePage(page) {
    protected val resourceSelect: Locator = page.locator("select").nth(0)
    protected val roleSelect: Locator = page.locator("select").nth(1)
    protected val subjectInput: Locator = page.locator("fieldset:nth-of-type(2) input").nth(0)
    protected val equipmentNoInput: Locator = page.locator("fieldset:nth-of-type(2) input").nth(1)
    protected val descriptionInput: Locator = page.locator("textarea")
    protected val submitButton: Locator = button("Отправить заявку")
    protected val errorAlert: Locator = page.locator(".alert.error").first()
    protected val ticketHistory: Locator = page.locator(".timeline")
}
