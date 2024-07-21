package com.caju.desafio.domain.transactions;

import com.caju.desafio.domain.DomainValidationException;

public record Amount(Double value) {
    public Amount {
        if(value <= 0){
            throw new DomainValidationException("Amount cannot be <= 0");
        }
    }
}
