package ru.servicedesk.autotest.ui.pages

import com.microsoft.playwright.Page
import io.qameta.allure.Step
import ru.servicedesk.autotest.ui.locators.ApprovalsLocators

class ApprovalsPage(page: Page) : ApprovalsLocators(page) {

    @Step("Открыть согласования")
    fun open() = apply { navigate("/approvals") }

    fun pendingCount(): Int = requestCards.count()

    @Step("Согласовать первую заявку в списке")
    fun approveFirst(comment: String? = null) = apply {
        open()
        comment?.let { firstCommentInput.fill(it) }
        firstApproveButton.click()
        page.waitForTimeout(600.0)
    }
}
