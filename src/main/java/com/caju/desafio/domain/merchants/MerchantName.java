package com.caju.desafio.domain.merchants;


import com.caju.desafio.domain.DomainValidationException;

public record MerchantName(String value) {
    public MerchantName {
        if(value == null || value.isBlank()) throw new DomainValidationException("MerchantName cannot be null");
    }
}
