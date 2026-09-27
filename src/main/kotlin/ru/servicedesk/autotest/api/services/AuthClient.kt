package ru.servicedesk.autotest.api.services

import io.qameta.allure.Step
import org.apache.http.HttpStatus.SC_UNAUTHORIZED
import ru.servicedesk.autotest.api.AbstractApiClient
import ru.servicedesk.autotest.api.Endpoints
import ru.servicedesk.autotest.api.models.ApiError
import ru.servicedesk.autotest.api.models.LoginRequest
import ru.servicedesk.autotest.api.parse
import ru.servicedesk.autotest.api.specs.SpecManager

class AuthClient : AbstractApiClient() {

    @Step("Вход {email} с неверными учётными данными — ожидается 401")
    fun loginRejected(email: String, password: String): ApiError =
        post(SpecManager.anonymous(), Endpoints.LOGIN, SC_UNAUTHORIZED, body = LoginRequest(email, password)).parse()
}
