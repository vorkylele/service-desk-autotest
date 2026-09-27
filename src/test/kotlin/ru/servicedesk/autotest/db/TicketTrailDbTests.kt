package ru.servicedesk.autotest.db

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.data.ControlExample
import ru.servicedesk.autotest.db.assertions.TicketTrailAssertions

@Epic("База данных")
@Feature("Журнал событий")
@Order(3)
class TicketTrailDbTests : BaseDbTest() {

    @Test
    @DisplayName("Заявка контрольного примера: три согласования и полный журнал событий")
    fun ticketHasFullTrail() {
        val decisions = tickets.approvalDecisions(ControlExample.ACCESS_TICKET_NUMBER)
        val statuses = tickets.statusTrail(ControlExample.ACCESS_TICKET_NUMBER)
        val breached = tickets.slaBreached(ControlExample.ACCESS_TICKET_NUMBER)
        TicketTrailAssertions.assertAllApproved(decisions, ControlExample.PAYMENT_GATEWAY_ROUTE.size)
        TicketTrailAssertions.assertFullAccessTrail(statuses)
        TicketTrailAssertions.assertWithinSla(breached)
    }

    @Test
    @DisplayName("У каждой заявки, созданной через систему, есть событие регистрации")
    fun everyTicketHasCreatedEvent() {
        val orphans = tickets.countWithoutCreatedEvent(ControlExample.ACCESS_TICKET_NUMBER)
        TicketTrailAssertions.assertNoTicketsWithoutCreatedEvent(orphans)
    }
}
