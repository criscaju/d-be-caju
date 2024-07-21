package com.caju.desafio.domain.merchants;

import com.caju.desafio.domain.DomainValidationException;

import java.util.UUID;

public record MerchantId(UUID value) {
    public MerchantId {
        if(value == null) throw new DomainValidationException("MerchantId cannot be null");
    }

    public MerchantId(){
        this(UUID.randomUUID());
    }
}
