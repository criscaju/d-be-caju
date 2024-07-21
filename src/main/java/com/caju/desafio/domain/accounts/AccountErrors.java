package com.caju.desafio.domain.accounts;

import com.caju.desafio.shared.Error;

public class AccountErrors {
    public static final Error NOT_FOUND = new Error("07", "Account not found");
    public static final Error INSUFFICIENT_FUNDS = new Error("51", "Account has insufficient funds");
}
