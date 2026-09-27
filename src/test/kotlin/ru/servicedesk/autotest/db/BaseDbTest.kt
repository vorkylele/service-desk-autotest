package ru.servicedesk.autotest.db

import ru.servicedesk.autotest.db.services.AccessGrantQueries
import ru.servicedesk.autotest.db.services.EmployeeQueries
import ru.servicedesk.autotest.db.services.TicketQueries

abstract class BaseDbTest {
    protected val accessGrants = AccessGrantQueries()
    protected val employees = EmployeeQueries()
    protected val tickets = TicketQueries()
}
