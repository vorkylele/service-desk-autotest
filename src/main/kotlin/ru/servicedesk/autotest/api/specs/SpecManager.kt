package ru.servicedesk.autotest.api.specs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.ObjectMapperConfig
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus.SC_OK
import ru.servicedesk.autotest.api.Endpoints
import ru.servicedesk.autotest.api.models.LoginRequest
import ru.servicedesk.autotest.config.Config
import java.util.concurrent.ConcurrentHashMap

/** Спецификации запросов: анонимная и от имени пользователя; токены запрашиваются один раз. */
object SpecManager {
    private val tokens = ConcurrentHashMap<String, String>()

    init {
        RestAssured.config = RestAssured.config().objectMapperConfig(
            ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory { _, _ ->
                jacksonObjectMapper()
                    .registerModule(JavaTimeModule())
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            },
        )
    }

    fun anonymous(): RequestSpecification = RequestSpecBuilder()
        .setBaseUri(Config.apiUrl)
        .setContentType(ContentType.JSON)
        .addFilter(AllureRestAssured())
        .build()

    fun asUser(email: String): RequestSpecification = RequestSpecBuilder()
        .addRequestSpecification(anonymous())
        .addHeader("Authorization", "Bearer ${token(email)}")
        .build()

    private fun token(email: String): String = tokens.computeIfAbsent(email) {
        RestAssured.given().spec(anonymous())
            .body(LoginRequest(email, Config.PASSWORD))
            .post(Endpoints.LOGIN)
            .then().statusCode(SC_OK)
            .extract().path("token")
    }
}
