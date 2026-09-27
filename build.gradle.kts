plugins {
    kotlin("jvm") version "2.0.21"
    id("io.qameta.allure") version "2.12.0"
}

group = "ru.servicedesk"
version = "1.0.0"

kotlin { jvmToolchain(21) }

repositories { mavenCentral() }

val allureVersion = "2.29.0"
val jacksonVersion = "2.17.2"

dependencies {
    implementation("com.microsoft.playwright:playwright:1.47.0")
    implementation("io.rest-assured:rest-assured:5.5.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.assertj:assertj-core:3.26.3")
    implementation("io.qameta.allure:allure-java-commons:$allureVersion")
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    implementation("org.slf4j:slf4j-api:2.0.16")

    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.16")
}

allure {
    version.set(allureVersion)
    adapter { frameworks { junit5 { adapterVersion.set(allureVersion) } } }
}

tasks.test {
    useJUnitPlatform()
    listOf("ui.url", "api.url", "db.url", "db.user", "db.password", "browser.channel", "headless").forEach { key ->
        System.getProperty(key)?.let { systemProperty(key, it) }
    }
    if (System.getProperty("browser.channel") != null) {
        environment("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1")   // используется установленный в системе браузер
    }
    testLogging { events("passed", "failed", "skipped") }
}

// Эталонные снимки экранных форм хранятся в репозитории клиентской части
tasks.register<Copy>("publishScreenshots") {
    from(layout.buildDirectory.dir("screenshots"))
    into("../service-desk-frontend/docs/screenshots")
}
