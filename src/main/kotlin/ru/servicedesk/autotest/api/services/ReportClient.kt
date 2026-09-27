package ru.servicedesk.autotest.api.services

import io.qameta.allure.Step
import io.restassured.RestAssured.given
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus.SC_FORBIDDEN
import org.apache.http.HttpStatus.SC_OK
import ru.servicedesk.autotest.api.AbstractApiClient
import ru.servicedesk.autotest.api.Endpoints
import ru.servicedesk.autotest.api.models.SlaReportRow
import ru.servicedesk.autotest.api.parse
import java.time.LocalDate

class ReportClient : AbstractApiClient() {

    @Step("Отчёт по нормативам срока за {from} — {to} от имени {email}")
    fun sla(email: String, from: LocalDate, to: LocalDate): List<SlaReportRow> =
        get(withPeriod(email, from, to), Endpoints.SLA_REPORT, SC_OK).parse()

    @Step("Отчёт по нормативам срока от имени {email} — ожидается 403")
    fun slaForbidden(email: String, from: LocalDate, to: LocalDate) {
        get(withPeriod(email, from, to), Endpoints.SLA_REPORT, SC_FORBIDDEN)
    }

    private fun withPeriod(email: String, from: LocalDate, to: LocalDate): RequestSpecification =
        given().spec(asUser(email)).queryParam("from", from.toString()).queryParam("to", to.toString())
}
