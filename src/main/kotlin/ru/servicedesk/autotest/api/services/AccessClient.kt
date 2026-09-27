package ru.servicedesk.autotest.api.services

import io.qameta.allure.Step
import org.apache.http.HttpStatus.SC_FORBIDDEN
import ru.servicedesk.autotest.api.AbstractApiClient
import ru.servicedesk.autotest.api.Endpoints
import ru.servicedesk.autotest.api.models.RevokeRequest

class AccessClient : AbstractApiClient() {

    @Step("Реестр прав от имени {email} — ожидается 403")
    fun registryForbidden(email: String) {
        get(asUser(email), Endpoints.ACCESS_GRANTS, SC_FORBIDDEN)
    }

    @Step("Отзыв права {grantId} от имени {email} — ожидается 403")
    fun revokeForbidden(email: String, grantId: Long, reason: String) {
        post(asUser(email), Endpoints.ACCESS_GRANT_REVOKE, SC_FORBIDDEN, grantId, body = RevokeRequest(reason))
    }

    @Step("Увольнение работника {employeeId} от имени {email} — ожидается 403")
    fun dismissForbidden(email: String, employeeId: Int) {
        post(asUser(email), Endpoints.EMPLOYEE_DISMISS, SC_FORBIDDEN, employeeId)
    }
}
