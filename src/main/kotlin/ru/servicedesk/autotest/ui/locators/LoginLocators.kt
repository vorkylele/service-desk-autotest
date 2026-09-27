package ru.servicedesk.autotest.ui.locators

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.ui.pages.BasePage

open class LoginLocators(page: Page) : BasePage(page) {
    protected val emailInput: Locator = page.locator("input[type=email]")
    protected val passwordInput: Locator = page.locator("input[type=password]")
    protected val loginButton: Locator = page.locator("button.primary")
    protected val sidebar: Locator = page.locator(".sidebar")
}
