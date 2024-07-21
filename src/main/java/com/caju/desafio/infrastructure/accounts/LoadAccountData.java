package com.caju.desafio.infrastructure.accounts;

import com.caju.desafio.application.accounts.AccountService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class LoadAccountData implements ApplicationListener<ApplicationReadyEvent> {
    private final AccountRepositoryImpl accountRepository;
    private final AccountService accountService;

    public LoadAccountData(AccountRepositoryImpl accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var initialAccount = accountService.getBaseAccount();
        if(!accountRepository.existsById(initialAccount.getId())) {
            accountRepository.save(initialAccount);
        }
    }
}
