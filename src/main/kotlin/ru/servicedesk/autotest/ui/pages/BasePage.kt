package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import ru.servicedesk.autotest.config.Config

abstract class BasePage(protected val page: Page) {

    protected fun navigate(path: String) {
        page.navigate(Config.uiUrl + path)
        page.waitForLoadState()
        page.waitForTimeout(400.0)
    }

    protected fun button(text: String): Locator = page.locator("button", Page.LocatorOptions().setHasText(text)).first()
}
