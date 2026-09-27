package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import ru.servicedesk.autotest.ui.locators.TicketCardLocators

class TicketCardPage(page: Page) : TicketCardLocators(page) {

    @Step("Открыть карточку заявки {ticketId}")
    fun open(ticketId: String) = apply {
        navigate("/ticket/$ticketId")
        history.waitFor()
    }

    fun ticketId(): String = page.url().substringAfterLast('/')

    fun number(): String = title.innerText().substringAfter("№ ").take(8)

    fun status(): String = statusBadge.innerText()

    fun routeSteps(): List<String> = routeSteps.allInnerTexts()

    fun history(): List<String> = historyItems.allInnerTexts()

    fun createdAt(): String = property("Создана").innerText()

    fun dueAt(): String = property("Контрольный срок").innerText()

    fun supportGroup(): String = property("Группа исполнителей").innerText()

    fun assignee(): String = property("Исполнитель").innerText()

    @Step("Принять заявку в работу")
    fun take() = apply {
        takeButton.click()
        page.waitForTimeout(500.0)
    }

    @Step("Ввести решение")
    fun typeResolution(text: String) = apply { resolutionInput.fill(text) }

    @Step("Решить заявку")
    fun resolve() = apply {
        resolveButton.click()
        page.waitForTimeout(600.0)
    }

    @Step("Подтвердить решение")
    fun confirm() = apply {
        confirmButton.click()
        page.waitForTimeout(500.0)
    }
}
