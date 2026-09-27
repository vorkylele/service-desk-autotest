package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import ru.servicedesk.autotest.ui.locators.LoginLocators

class LoginPage(page: Page) : LoginLocators(page) {

    @Step("Открыть страницу входа")
    fun open() = apply { navigate("/login") }

    @Step("Ввести учётные данные {email}")
    fun fillCredentials(email: String, password: String) = apply {
        emailInput.fill(email)
        passwordInput.fill(password)
    }

    @Step("Войти")
    fun submit() = apply {
        loginButton.click()
        sidebar.waitFor()
    }
}
