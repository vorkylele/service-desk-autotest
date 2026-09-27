package ru.servicedesk.autotest.api.services

import io.qameta.allure.Step
import io.restassured.RestAssured.given
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus.SC_CONFLICT
import org.apache.http.HttpStatus.SC_CREATED
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.apache.http.HttpStatus.SC_OK
import ru.servicedesk.autotest.api.AbstractApiClient
import ru.servicedesk.autotest.api.Endpoints
import ru.servicedesk.autotest.api.models.ApiError
import ru.servicedesk.autotest.api.models.NewTicketRequest
import ru.servicedesk.autotest.api.models.TicketDetailsResponse
import ru.servicedesk.autotest.api.models.TicketResponse
import ru.servicedesk.autotest.api.models.TicketScope
import ru.servicedesk.autotest.api.parse

class TicketClient : AbstractApiClient() {

    @Step("Создание заявки от имени {email}")
    fun create(email: String, request: NewTicketRequest): TicketResponse =
        post(asUser(email), Endpoints.TICKETS, SC_CREATED, body = request).parse()

    @Step("Создание заявки от имени {email} — ожидается отказ 409")
    fun createRejected(email: String, request: NewTicketRequest): ApiError =
        post(asUser(email), Endpoints.TICKETS, SC_CONFLICT, body = request).parse()

    @Step("Список заявок {scope} от имени {email}")
    fun list(email: String, scope: TicketScope): List<TicketResponse> =
        get(withScope(email, scope), Endpoints.TICKETS, SC_OK).parse()

    @Step("Список заявок {scope} от имени {email} — ожидается 403")
    fun listForbidden(email: String, scope: TicketScope) {
        get(withScope(email, scope), Endpoints.TICKETS, SC_FORBIDDEN)
    }

    @Step("Поиск заявки № {number} в журнале от имени {email}")
    fun findByNumber(email: String, number: String): TicketResponse =
        list(email, TicketScope.ALL).first { it.number == number }

    @Step("Карточка заявки {ticketId} от имени {email}")
    fun details(email: String, ticketId: Long): TicketDetailsResponse =
        get(asUser(email), Endpoints.TICKET, SC_OK, ticketId).parse()

    @Step("Карточка заявки {ticketId} от имени {email} — ожидается 403")
    fun detailsForbidden(email: String, ticketId: Long) {
        get(asUser(email), Endpoints.TICKET, SC_FORBIDDEN, ticketId)
    }

    @Step("Принятие заявки {ticketId} в работу от имени {email}")
    fun take(email: String, ticketId: Long): TicketResponse =
        post(asUser(email), Endpoints.TICKET_TAKE, SC_OK, ticketId).parse()

    @Step("Принятие заявки {ticketId} в работу от имени {email} — ожидается 403")
    fun takeForbidden(email: String, ticketId: Long) {
        post(asUser(email), Endpoints.TICKET_TAKE, SC_FORBIDDEN, ticketId)
    }

    private fun withScope(email: String, scope: TicketScope): RequestSpecification =
        given().spec(asUser(email)).queryParam("scope", scope.value)
}
