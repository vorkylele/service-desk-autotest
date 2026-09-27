package ru.servicedesk.autotest.api.models.builders

import ru.servicedesk.autotest.api.helpers.ReferenceIds
import ru.servicedesk.autotest.api.models.NewTicketRequest

object TicketRequests {

    fun incident(typeCode: String, subject: String, description: String, priorityCode: Int? = null) =
        NewTicketRequest(ReferenceIds.typeId(typeCode), subject, description, priorityCode)

    fun access(typeCode: String, resourceCode: String, roleCode: String, subject: String, description: String) =
        NewTicketRequest(
            typeId = ReferenceIds.typeId(typeCode),
            subject = subject,
            description = description,
            resourceId = ReferenceIds.resourceId(resourceCode),
            accessRoleId = ReferenceIds.roleId(resourceCode, roleCode),
        )
}
