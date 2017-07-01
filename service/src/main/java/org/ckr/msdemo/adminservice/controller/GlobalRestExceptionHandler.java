package org.ckr.msdemo.adminservice.controller;

import org.ckr.msdemo.exception.handler.RestExceptionHandler;
import org.ckr.msdemo.exception.valueobject.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * The global exception handler for all REST API.
 * It just catch all exception and call {@link RestExceptionHandler#handleException(Throwable, AbstractMessageSource)}
 * to generate response.
 */
@ControllerAdvice(annotations = RestController.class)
public class GlobalRestExceptionHandler {

    @Autowired
    private AbstractMessageSource messageSource;

    /**
     * This is the exception handler method that catch all exceptions.
     * @param exp Exception is caught
     * @return    an instance of ResponseEntity for HTTP response.
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorResponse> handleException(final Throwable exp) {
        return RestExceptionHandler.handleException(exp, messageSource);
    }

}
