package ru.servicedesk.autotest.db

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.db.assertions.EmployeeAssertions

@Epic("База данных")
@Feature("Защита данных")
@Order(3)
class EmployeeDbTests : BaseDbTest() {

    @Test
    @DisplayName("Пароли хранятся только в виде хеша bcrypt")
    fun passwordsAreHashed() {
        val plainPasswords = employees.countPlainPasswords()
        EmployeeAssertions.assertNoPlainPasswords(plainPasswords)
    }
}
