package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import ru.servicedesk.autotest.ui.locators.AccessRegistryLocators

class AccessRegistryPage(page: Page) : AccessRegistryLocators(page) {

    @Step("Открыть реестр прав доступа")
    fun open() = apply { navigate("/access") }

    @Step("Показать отозванные права")
    fun showRevoked() = apply { onlyActiveCheckbox.uncheck() }

    fun firstRow(): String = rows.first().innerText()

    fun firstRevokedRow(): String = revokedRows.first().innerText()
}
