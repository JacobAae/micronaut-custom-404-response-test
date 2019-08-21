package com.demo

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import spock.lang.AutoCleanup
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class NotFoundCustomClientSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @Client(value = "/", errorType = MyError)
    static interface CustomErrorClient {
        @Get("/unknownField")
        HttpResponse<MyError> index()
    }

    void "test 404 status with custom client"() {
        setup:
        CustomErrorClient client = embeddedServer.getApplicationContext().getBean(CustomErrorClient)

        when:
        HttpResponse<MyError> response = client.index()

        then:
        response.status == HttpStatus.NOT_FOUND
        response.body.isPresent()

        response.body().myErrorMessage == 'Some message'
        response.body().myErrorCode == 'Some error code'
    }

}
