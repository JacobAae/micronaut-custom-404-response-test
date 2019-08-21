package com.demo

import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpStatus
import spock.lang.AutoCleanup
import spock.lang.Specification

import javax.inject.Inject


@MicronautTest
class NotFoundControllerSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @AutoCleanup @Inject @Client(value = "/", errorType = MyError)
    RxHttpClient client

    void "test 404 status"() {
        when:
        client.toBlocking().exchange("/notFound", MyError)

        then:
        HttpClientResponseException exception = thrown(HttpClientResponseException)
        exception.response.status == HttpStatus.NOT_FOUND

        Optional<MyError> body = exception.response.getBody(MyError)
        body.present
        body.get().myErrorMessage == 'Some message'
        body.get().myErrorCode == 'Some error code'
    }

}
