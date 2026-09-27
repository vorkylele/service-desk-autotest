package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import ru.servicedesk.autotest.ui.locators.EmployeesLocators

class EmployeesPage(page: Page) : EmployeesLocators(page) {

    @Step("Открыть список работников")
    fun open() = apply { navigate("/employees") }

    @Step("Оформить увольнение работника «{employeeName}»")
    fun dismiss(employeeName: String): String {
        dismissButton(employeeName).click()
        infoAlert.waitFor()
        return infoAlert.innerText()
    }
}
