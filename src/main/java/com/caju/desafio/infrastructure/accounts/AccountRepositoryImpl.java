package com.caju.desafio.infrastructure.accounts;

import com.caju.desafio.domain.accounts.Account;
import com.caju.desafio.domain.accounts.AccountId;
import com.caju.desafio.domain.accounts.IAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements IAccountRepository {

    private final MongoAccountRepository accountRepository;

    public AccountRepositoryImpl(MongoAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findById(AccountId id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Boolean existsById(AccountId id) {
        return accountRepository.existsById(id);
    }


}
