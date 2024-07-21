package com.caju.desafio.domain.merchants;


import com.caju.desafio.domain.DomainValidationException;

public record Mcc(String value) {
    public Mcc {
        if(value == null || value.isBlank()) throw new DomainValidationException("Mcc cannot be null");
    }
}
