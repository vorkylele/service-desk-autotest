package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import ru.servicedesk.autotest.api.models.TicketScope

class TicketListPage(page: Page) : BasePage(page) {

    @Step("Открыть список заявок {scope}")
    fun open(scope: TicketScope) = apply { navigate("/tickets/${scope.value}") }
}
