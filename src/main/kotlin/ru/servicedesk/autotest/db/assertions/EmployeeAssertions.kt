package ru.servicedesk.autotest.db.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat

object EmployeeAssertions {

    @Step("Проверка: пароли хранятся только в виде хеша bcrypt")
    fun assertNoPlainPasswords(count: Long) {
        assertThat(count).isZero()
    }
}
