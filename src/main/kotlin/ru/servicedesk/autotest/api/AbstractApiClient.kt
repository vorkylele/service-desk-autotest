package ru.servicedesk.autotest.api

import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.slf4j.LoggerFactory
import ru.servicedesk.autotest.api.specs.SpecManager

abstract class AbstractApiClient {
    private val log = LoggerFactory.getLogger(javaClass)

    protected fun asUser(email: String): RequestSpecification = SpecManager.asUser(email)

    protected fun get(spec: RequestSpecification, path: String, status: Int, vararg pathParams: Any): Response =
        checked("GET", path, status, given().spec(spec).get(path, *pathParams))

    protected fun post(spec: RequestSpecification, path: String, status: Int, vararg pathParams: Any, body: Any? = null): Response {
        val request = given().spec(spec)
        if (body != null) request.body(body)
        return checked("POST", path, status, request.post(path, *pathParams))
    }

    private fun checked(method: String, path: String, status: Int, response: Response): Response {
        log.info("{} {} -> {}", method, path, response.statusCode)
        return response.then().statusCode(status).extract().response()
    }
}

internal inline fun <reified T> Response.parse(): T = `as`(object : TypeRef<T>() {})
