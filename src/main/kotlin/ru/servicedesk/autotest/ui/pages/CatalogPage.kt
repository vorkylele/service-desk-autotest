package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Page
import com.microsoft.playwright.options.LoadState
import io.qameta.allure.Step
import ru.servicedesk.autotest.ui.locators.CatalogLocators

class CatalogPage(page: Page) : CatalogLocators(page) {

    @Step("Открыть каталог услуг")
    fun open() = apply { navigate("/catalog") }

    @Step("Выбрать услугу «{serviceName}»")
    fun choose(serviceName: String): TicketFormPage {
        service(serviceName).click()
        ticketForm.waitFor()
        page.waitForLoadState(LoadState.NETWORKIDLE)
        return TicketFormPage(page)
    }
}
