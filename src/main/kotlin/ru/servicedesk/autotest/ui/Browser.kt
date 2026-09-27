package ru.servicedesk.autotest.ui

import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import io.qameta.allure.Allure
import io.qameta.allure.Step
import ru.servicedesk.autotest.config.Config
import java.io.ByteArrayInputStream
import java.nio.file.Files
import java.nio.file.Paths

/** Браузер на весь прогон; каждый вход выполняется в новом контексте, как у нового пользователя. */
class Browser : AutoCloseable {
    private val playwright: Playwright = Playwright.create()
    private val chromium: com.microsoft.playwright.Browser = playwright.chromium().launch(
        BrowserType.LaunchOptions().setHeadless(Config.headless).apply {
            if (Config.browserChannel.isNotBlank()) setChannel(Config.browserChannel)
        },
    )
    private var context: BrowserContext? = null

    lateinit var page: Page
        private set

    fun newSession(): Page {
        context?.close()
        context = chromium.newContext(
            com.microsoft.playwright.Browser.NewContextOptions().setViewportSize(1440, 900).setLocale("ru-RU"),
        )
        page = context!!.newPage().apply { onDialog { it.accept() } }
        return page
    }

    @Step("Снимок экрана: {name}")
    fun screenshot(name: String) {
        page.waitForTimeout(400.0)
        val file = Paths.get("build", "screenshots", "$name.png")
        Files.createDirectories(file.parent)
        val bytes = page.screenshot(Page.ScreenshotOptions().setPath(file))
        Allure.addAttachment(name, "image/png", ByteArrayInputStream(bytes), "png")
    }

    override fun close() {
        context?.close()
        chromium.close()
        playwright.close()
    }
}
