package com.caju.desafio.application.transactions;

import java.util.UUID;


public record CreateTransactionRequest(UUID accountId, Double totalAmount, String mcc, String merchant) {
}
