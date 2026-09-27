package ru.servicedesk.autotest.ui.locators

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.ui.pages.BasePage

open class ApprovalsLocators(page: Page) : BasePage(page) {
    protected val requestCards: Locator = page.locator(".card", Page.LocatorOptions().setHasText("запрашивает роль"))
    protected val firstCommentInput: Locator = requestCards.first().locator("input")
    protected val firstApproveButton: Locator =
        requestCards.first().locator("button", Locator.LocatorOptions().setHasText("Согласовать"))
}
