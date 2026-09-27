package ru.servicedesk.autotest.ui.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.ui.pages.AccessRegistryPage

object AccessRegistryAssertions {

    @Step("Проверка: первая запись реестра содержит {fragments}")
    fun assertFirstRowContains(page: AccessRegistryPage, vararg fragments: String) {
        assertThat(page.firstRow()).contains(*fragments)
    }

    @Step("Проверка: первая отозванная запись содержит {fragments}")
    fun assertFirstRevokedRowContains(page: AccessRegistryPage, vararg fragments: String) {
        assertThat(page.firstRevokedRow()).contains(*fragments)
    }
}
