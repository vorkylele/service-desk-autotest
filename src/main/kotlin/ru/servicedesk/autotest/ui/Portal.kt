package ru.servicedesk.autotest.ui

import io.qameta.allure.Step
import ru.servicedesk.autotest.api.models.TicketScope
import ru.servicedesk.autotest.config.Config
import ru.servicedesk.autotest.ui.pages.AccessRegistryPage
import ru.servicedesk.autotest.ui.pages.ApprovalsPage
import ru.servicedesk.autotest.ui.pages.CatalogPage
import ru.servicedesk.autotest.ui.pages.EmployeesPage
import ru.servicedesk.autotest.ui.pages.LoginPage
import ru.servicedesk.autotest.ui.pages.ReportsPage
import ru.servicedesk.autotest.ui.pages.TicketCardPage
import ru.servicedesk.autotest.ui.pages.TicketListPage

class Portal(private val browser: Browser) {

    fun loginPage(): LoginPage = LoginPage(browser.newSession()).open()

    @Step("Вход в систему: {email}")
    fun loginAs(email: String): Portal = apply { loginPage().fillCredentials(email, Config.PASSWORD).submit() }

    fun catalog(): CatalogPage = CatalogPage(browser.page).open()

    fun tickets(scope: TicketScope): TicketListPage = TicketListPage(browser.page).open(scope)

    fun ticketCard(ticketId: String): TicketCardPage = TicketCardPage(browser.page).open(ticketId)

    fun approvals(): ApprovalsPage = ApprovalsPage(browser.page).open()

    fun accessRegistry(): AccessRegistryPage = AccessRegistryPage(browser.page).open()

    fun reports(): ReportsPage = ReportsPage(browser.page).open()

    fun employees(): EmployeesPage = EmployeesPage(browser.page).open()

    fun screenshot(name: String) = browser.screenshot(name)
}
