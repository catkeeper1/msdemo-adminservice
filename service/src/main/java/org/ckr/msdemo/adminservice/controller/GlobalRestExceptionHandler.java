package org.ckr.msdemo.adminservice.controller;

import org.ckr.msdemo.exception.handler.RestExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@ControllerAdvice(annotations = RestController.class)
public class GlobalRestExceptionHandler {

    @Autowired
    private AbstractMessageSource messageSource;

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity handleException(final Throwable exp) {
        return RestExceptionHandler.handleException(exp, messageSource);
    }

}
