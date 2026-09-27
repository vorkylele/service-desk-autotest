package ru.servicedesk.autotest.db

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.config.Users
import ru.servicedesk.autotest.data.ControlExample
import ru.servicedesk.autotest.db.assertions.AccessGrantAssertions

@Epic("База данных")
@Feature("Реестр прав доступа")
@Order(3)
class AccessGrantDbTests : BaseDbTest() {

    @Test
    @DisplayName("Решённая заявка на доступ оставила запись реестра со ссылкой на заявку-основание")
    fun grantIsLinkedToTicket() {
        val grants = accessGrants.grantsOf(Users.APPLICANT)
        AccessGrantAssertions.assertSingleActiveGrant(grants, ControlExample.ACCESS_TICKET_NUMBER, "R003", "VIEW")
    }

    @Test
    @DisplayName("После увольнения у работника нет действующих прав, основание отзыва записано")
    fun dismissalLeftNoActiveGrants() {
        val employee = employees.byEmail(Users.DEVELOPER)
        val grants = accessGrants.grantsOf(Users.DEVELOPER)
        AccessGrantAssertions.assertDismissed(employee)
        AccessGrantAssertions.assertAllRevoked(grants, ControlExample.DISMISSAL_REASON)
    }

    @Test
    @DisplayName("База не допускает второго действующего права на ту же роль")
    fun duplicateActiveGrantIsRejectedByDatabase() {
        val error = accessGrants.tryInsertDuplicateOfActiveGrant(Users.APPLICANT)
        AccessGrantAssertions.assertUniqueActiveGrantViolation(error)
    }
}
