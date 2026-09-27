package ru.servicedesk.autotest.api

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.api.assertions.AuthAssertions
import ru.servicedesk.autotest.config.Config
import ru.servicedesk.autotest.config.Users

@Epic("REST API")
@Feature("Аутентификация")
@Order(2)
class AuthApiTests : BaseApiTest() {

    @Test
    @DisplayName("Неверный пароль — 401 без раскрытия причины")
    fun wrongPasswordIsRejected() {
        val error = authClient.loginRejected(Users.APPLICANT, "wrong")
        AuthAssertions.assertReasonIsHidden(error)
    }

    @Test
    @DisplayName("Запрос без токена — 401")
    fun requestWithoutTokenIsRejected() {
        catalogClient.catalogWithoutToken()
    }

    @Test
    @DisplayName("Уволенный работник не может войти")
    fun dismissedEmployeeCannotLogin() {
        authClient.loginRejected(Users.DEVELOPER, Config.PASSWORD)
    }
}
