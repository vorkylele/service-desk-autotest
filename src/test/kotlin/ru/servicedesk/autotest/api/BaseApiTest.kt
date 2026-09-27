package ru.servicedesk.autotest.api

import ru.servicedesk.autotest.api.services.AccessClient
import ru.servicedesk.autotest.api.services.ApprovalClient
import ru.servicedesk.autotest.api.services.AuthClient
import ru.servicedesk.autotest.api.services.CatalogClient
import ru.servicedesk.autotest.api.services.ReportClient
import ru.servicedesk.autotest.api.services.TicketClient

abstract class BaseApiTest {
    protected val authClient = AuthClient()
    protected val catalogClient = CatalogClient()
    protected val ticketClient = TicketClient()
    protected val approvalClient = ApprovalClient()
    protected val accessClient = AccessClient()
    protected val reportClient = ReportClient()
}
