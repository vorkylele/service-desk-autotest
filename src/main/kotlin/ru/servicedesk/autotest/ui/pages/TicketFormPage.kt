package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.WaitForSelectorState
import io.qameta.allure.Step
import ru.servicedesk.autotest.ui.locators.TicketFormLocators

class TicketFormPage(page: Page) : TicketFormLocators(page) {

    @Step("Заполнить заявку на доступ: система «{resource}», роль «{role}»")
    fun fillAccess(resource: String, role: String, justification: String) = apply {
        selectContaining(resourceSelect, resource)
        selectContaining(roleSelect, role)
        descriptionInput.fill(justification)
    }

    @Step("Заполнить инцидент: «{subject}»")
    fun fillIncident(subject: String, equipmentNo: String, description: String) = apply {
        subjectInput.fill(subject)
        equipmentNoInput.fill(equipmentNo)
        descriptionInput.fill(description)
    }

    @Step("Отправить заявку")
    fun submit(): TicketCardPage {
        submitButton.click()
        ticketHistory.waitFor()
        return TicketCardPage(page)
    }

    @Step("Отправить заявку, ожидая отказ системы")
    fun submitExpectingError(): String {
        submitButton.click()
        errorAlert.waitFor()
        return errorAlert.innerText()
    }

    /** Подписи пунктов списка содержат код и название, поэтому пункт ищется по вхождению текста. */
    private fun selectContaining(select: Locator, text: String) {
        val option = select.locator("option", Locator.LocatorOptions().setHasText(text)).first()
        option.waitFor(Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED))
        select.selectOption(option.getAttribute("value"))
    }
}
