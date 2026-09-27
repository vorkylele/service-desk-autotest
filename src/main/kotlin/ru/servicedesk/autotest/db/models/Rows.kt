package ru.servicedesk.autotest.db.models

import java.time.LocalDateTime

data class GrantRow(
    val ticketNumber: String?,
    val resourceCode: String,
    val roleCode: String,
    val revokedAt: LocalDateTime?,
    val revokeReason: String?,
)

data class EmployeeRow(val active: Boolean, val dismissedAt: LocalDateTime?)
