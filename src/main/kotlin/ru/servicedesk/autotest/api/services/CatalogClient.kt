package ru.servicedesk.autotest.api.services

import io.qameta.allure.Step
import org.apache.http.HttpStatus.SC_OK
import org.apache.http.HttpStatus.SC_UNAUTHORIZED
import ru.servicedesk.autotest.api.AbstractApiClient
import ru.servicedesk.autotest.api.Endpoints
import ru.servicedesk.autotest.api.models.AccessRoleResponse
import ru.servicedesk.autotest.api.models.ResourceResponse
import ru.servicedesk.autotest.api.models.TicketTypeResponse
import ru.servicedesk.autotest.api.parse
import ru.servicedesk.autotest.api.specs.SpecManager

class CatalogClient : AbstractApiClient() {

    @Step("Каталог услуг от имени {email}")
    fun catalog(email: String): List<TicketTypeResponse> = get(asUser(email), Endpoints.CATALOG, SC_OK).parse()

    @Step("Каталог услуг без токена — ожидается 401")
    fun catalogWithoutToken() {
        get(SpecManager.anonymous(), Endpoints.CATALOG, SC_UNAUTHORIZED)
    }

    @Step("Информационные ресурсы от имени {email}")
    fun resources(email: String): List<ResourceResponse> = get(asUser(email), Endpoints.RESOURCES, SC_OK).parse()

    @Step("Роли ресурса {resourceId}")
    fun roles(email: String, resourceId: Int): List<AccessRoleResponse> =
        get(asUser(email), Endpoints.RESOURCE_ROLES, SC_OK, resourceId).parse()
}
