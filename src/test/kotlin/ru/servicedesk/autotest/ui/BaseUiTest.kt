package ru.servicedesk.autotest.ui

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseUiTest {
    private val browser = Browser()
    protected val portal = Portal(browser)

    @AfterAll
    fun closeBrowser() {
        browser.close()
    }
}
