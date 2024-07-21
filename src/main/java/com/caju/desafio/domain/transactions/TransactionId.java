package com.caju.desafio.domain.transactions;

import com.caju.desafio.domain.DomainValidationException;

import java.util.UUID;

public record TransactionId(UUID value) {
    public TransactionId {
        if(value == null) throw new DomainValidationException("TransactionId cannot be null");
    }

    public TransactionId(){
        this(UUID.randomUUID());
    }
}
