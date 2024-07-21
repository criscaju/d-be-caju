package com.caju.desafio.domain;

public class DomainValidationException extends RuntimeException{
    public DomainValidationException(String message) {
        super(message);
    }
}
