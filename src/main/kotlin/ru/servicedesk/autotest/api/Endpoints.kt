package ru.servicedesk.autotest.api

object Endpoints {
    const val LOGIN = "/api/auth/login"
    const val CATALOG = "/api/catalog"
    const val RESOURCES = "/api/resources"
    const val RESOURCE_ROLES = "/api/resources/{id}/roles"
    const val TICKETS = "/api/tickets"
    const val TICKET = "/api/tickets/{id}"
    const val TICKET_TAKE = "/api/tickets/{id}/take"
    const val APPROVALS = "/api/approvals"
    const val APPROVAL_DECISION = "/api/approvals/{id}/decision"
    const val ACCESS_GRANTS = "/api/access-grants"
    const val ACCESS_GRANT_REVOKE = "/api/access-grants/{id}/revoke"
    const val EMPLOYEE_DISMISS = "/api/employees/{id}/dismiss"
    const val SLA_REPORT = "/api/reports/sla"
}
