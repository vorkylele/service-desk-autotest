package ru.servicedesk.autotest.ui.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat

object EmployeesAssertions {

    @Step("Проверка: учётная запись заблокирована, отозвано прав — {revokedGrants}")
    fun assertDismissed(message: String, revokedGrants: Int) {
        assertThat(message).contains("учётная запись заблокирована", "отозвано прав доступа — $revokedGrants")
    }
}
