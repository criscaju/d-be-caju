package com.caju.desafio.application.accounts;

import com.caju.desafio.domain.accounts.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    public Account getBaseAccount() {
        return Account.createBaseAccount();
    }
}
