package com.caju.desafio.presentation;

import com.caju.desafio.domain.DomainValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class DomainExceptionsHandler {

    private static final Logger LOGGER = Logger.getLogger(DomainExceptionsHandler.class.getName());

    @ExceptionHandler({DomainValidationException.class})
    public ResponseEntity<ResponseRecord> handleDomainExceptions(DomainValidationException ex){
        LOGGER.log(Level.INFO, "Error while validating domain: ".concat(ex.getMessage()));

        return new ResponseEntity<>(new ResponseRecord("07"), HttpStatus.OK);
    }
}
