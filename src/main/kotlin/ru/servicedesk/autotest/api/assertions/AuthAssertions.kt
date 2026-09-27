package ru.servicedesk.autotest.api.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.api.models.ApiError

object AuthAssertions {

    @Step("Проверка: причина отказа во входе не раскрывается")
    fun assertReasonIsHidden(error: ApiError) {
        assertThat(error.message).isEqualTo("Неверный адрес электронной почты или пароль")
    }
}
