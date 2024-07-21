package com.caju.desafio.domain.accounts;

import com.caju.desafio.domain.DomainValidationException;

import java.util.UUID;

public record AccountId(UUID value) {
    public AccountId {
        if(value == null) throw new DomainValidationException("AccountId cannot be null");
    }

    public AccountId(){
        this(UUID.randomUUID());
    }
}
