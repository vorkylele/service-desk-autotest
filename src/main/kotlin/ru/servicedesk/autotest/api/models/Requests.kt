package ru.servicedesk.autotest.api.models

data class LoginRequest(val email: String, val password: String)

data class NewTicketRequest(
    val typeId: Int,
    val subject: String,
    val description: String,
    val priorityCode: Int? = null,
    val resourceId: Int? = null,
    val accessRoleId: Int? = null,
)

data class DecisionRequest(val approved: Boolean, val comment: String? = null)

data class RevokeRequest(val reason: String)

enum class TicketScope(val value: String) { MY("my"), QUEUE("queue"), ALL("all") }
