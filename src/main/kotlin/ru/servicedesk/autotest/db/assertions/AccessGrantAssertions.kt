package ru.servicedesk.autotest.db.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.db.models.EmployeeRow
import ru.servicedesk.autotest.db.models.GrantRow

object AccessGrantAssertions {

    @Step("Проверка: единственное действующее право {resourceCode}/{roleCode} по заявке № {ticketNumber}")
    fun assertSingleActiveGrant(grants: List<GrantRow>, ticketNumber: String, resourceCode: String, roleCode: String) {
        assertThat(grants).hasSize(1)
        assertThat(grants.single().ticketNumber).isEqualTo(ticketNumber)
        assertThat(grants.single().resourceCode).isEqualTo(resourceCode)
        assertThat(grants.single().roleCode).isEqualTo(roleCode)
        assertThat(grants.single().revokedAt).isNull()
    }

    @Step("Проверка: учётная запись заблокирована, дата увольнения записана")
    fun assertDismissed(employee: EmployeeRow) {
        assertThat(employee.active).isFalse()
        assertThat(employee.dismissedAt).isNotNull()
    }

    @Step("Проверка: все права отозваны с основанием «{reason}»")
    fun assertAllRevoked(grants: List<GrantRow>, reason: String) {
        assertThat(grants).isNotEmpty.allSatisfy {
            assertThat(it.revokedAt).isNotNull()
            assertThat(it.revokeReason).isEqualTo(reason)
        }
    }

    @Step("Проверка: база отклонила второе действующее право по уникальному индексу")
    fun assertUniqueActiveGrantViolation(error: String?) {
        assertThat(error).contains("uq_active_grant")
    }
}
