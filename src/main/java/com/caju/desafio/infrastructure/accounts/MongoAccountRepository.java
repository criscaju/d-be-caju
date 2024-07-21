package com.caju.desafio.infrastructure.accounts;

import com.caju.desafio.domain.accounts.Account;
import com.caju.desafio.domain.accounts.AccountId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAccountRepository extends MongoRepository<Account, AccountId> {
}
