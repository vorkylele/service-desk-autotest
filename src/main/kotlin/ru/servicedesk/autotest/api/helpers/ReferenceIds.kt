package ru.servicedesk.autotest.api.helpers

import ru.servicedesk.autotest.api.services.CatalogClient
import ru.servicedesk.autotest.config.Users

/** Идентификаторы справочников по их кодам; справочники неизменны, поэтому читаются один раз. */
object ReferenceIds {
    private val catalog = CatalogClient()
    private val types by lazy { catalog.catalog(Users.APPLICANT) }
    private val resources by lazy { catalog.resources(Users.APPLICANT) }

    fun typeId(code: String): Int = types.first { it.code == code }.id

    fun resourceId(code: String): Int = resources.first { it.code == code }.id

    fun roleId(resourceCode: String, roleCode: String): Int =
        catalog.roles(Users.APPLICANT, resourceId(resourceCode)).first { it.code == roleCode }.id
}
