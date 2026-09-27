package ru.servicedesk.autotest.api

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import ru.servicedesk.autotest.api.assertions.CatalogAssertions
import ru.servicedesk.autotest.config.Users

@Epic("REST API")
@Feature("Справочники")
@Order(2)
class CatalogApiTests : BaseApiTest() {

    @Test
    @DisplayName("Каталог услуг содержит шесть видов заявок двух категорий")
    fun catalogHasSixTypes() {
        val catalog = catalogClient.catalog(Users.APPLICANT)
        CatalogAssertions.assertSixTypesInTwoCategories(catalog)
    }
}
