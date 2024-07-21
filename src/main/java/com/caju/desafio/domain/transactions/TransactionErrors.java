package com.caju.desafio.domain.transactions;

import com.caju.desafio.shared.Error;

public class TransactionErrors {
    public static final Error INVALID_ACCOUNT_ID = new Error("07", "AccountId cannot be null");
    public static final Error INVALID_MCC = new Error("07", "Mcc cannot be null or empty");
    public static final Error INVALID_AMOUNT = new Error("07", "Amount cannot be zero or less");
    public static final Error INVALID_MERCHANT = new Error("07", "Merchant cannot be null or empty");
}
