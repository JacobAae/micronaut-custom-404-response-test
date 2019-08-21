package com.demo

import groovy.util.logging.Slf4j
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.HttpStatus

@Slf4j
@Controller("/notFound")
class NotFoundController {

    @Error(status = HttpStatus.NOT_FOUND, global = true)
    HttpResponse<MyError> notFound(HttpRequest request) {
        log.trace("Page not found: ${request.uri}" )
        HttpResponse.notFound().body(
                new MyError(
                        myErrorCode: 'Some error code',
                        myErrorMessage: 'Some message'
                )
        )
    }

}