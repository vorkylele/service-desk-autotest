package ru.servicedesk.autotest.api.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions.assertThat
import ru.servicedesk.autotest.api.models.TicketTypeResponse

object CatalogAssertions {

    @Step("Проверка: каталог из шести видов заявок двух категорий")
    fun assertSixTypesInTwoCategories(catalog: List<TicketTypeResponse>) {
        assertThat(catalog.map { it.code }).containsExactly("101", "102", "103", "201", "202", "203")
        assertThat(catalog.count { it.category == "ACCESS" }).isEqualTo(3)
    }
}
