package com.caju.desafio.domain.accounts;

import java.util.List;

public interface IAccountRepository {
     Account findById(AccountId id);
     Account save(Account account);
     List<Account> findAll();
     Boolean existsById(AccountId id);
}
