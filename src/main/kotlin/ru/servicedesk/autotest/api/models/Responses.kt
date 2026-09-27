package ru.servicedesk.autotest.api.models

import java.time.LocalDateTime

data class ApiError(val message: String)

data class TicketTypeResponse(val id: Int, val code: String, val category: String)

data class ResourceResponse(val id: Int, val code: String)

data class AccessRoleResponse(val id: Int, val code: String)

data class TicketResponse(
    val id: Long,
    val number: String,
    val statusCode: String,
    val assignee: String?,
    val createdAt: LocalDateTime,
    val dueAt: LocalDateTime,
)

data class ApprovalResponse(val id: Long, val ticketId: Long, val kind: String, val decision: String)

data class TicketDetailsResponse(val ticket: TicketResponse, val approvals: List<ApprovalResponse>)

data class SlaReportRow(val typeCode: String, val total: Int, val breached: Int)
