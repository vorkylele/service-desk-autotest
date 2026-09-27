package ru.servicedesk.autotest.api.models.builders

import ru.servicedesk.autotest.api.models.DecisionRequest

object ApprovalRequests {

    fun approve() = DecisionRequest(approved = true)

    fun reject(reason: String? = null) = DecisionRequest(approved = false, comment = reason)
}
