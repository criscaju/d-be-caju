package com.caju.desafio.domain.accounts;

import com.caju.desafio.domain.transactions.Amount;
import com.caju.desafio.shared.Result;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Document("accounts")
public class Account {

    private AccountId id;
    private Map<BalanceType, Double> balance;

    public AccountId getId() {
        return id;
    }

    public Map<BalanceType, Double> getBalance() {
        return balance;
    }

    private void setId(AccountId id) {
        this.id = id;
    }

    private void setBalance(Map<BalanceType, Double> balance) {
        this.balance = balance;
    }

    private Account() {

    }

    public static Account createAccount(Map<BalanceType, Double> balance) {
        Account account = new Account();
        account.setId(new AccountId());
        account.setBalance(balance);
        return account;
    }

    public Result debitFromBalance(BalanceType balanceType, Amount amount) {
        Double currentBalance = this.getBalance().get(balanceType);

        if(currentBalance != null && currentBalance >= amount.value()){
            this.getBalance().replace(balanceType, currentBalance - amount.value());
            return Result.success();
        }
        return Result.failure(AccountErrors.INSUFFICIENT_FUNDS);
    }

    public Result debitFromCashBalance(Amount amount) {
        return debitFromBalance(BalanceType.CASH, amount);
    }

    public static Account createBaseAccount() {
        Map<BalanceType, Double> balance = new HashMap<>();
        balance.put(BalanceType.CASH, 500d);
        balance.put(BalanceType.MEAL, 450d);
        balance.put(BalanceType.FOOD, 350d);

        Account account = new Account();
        account.setId(new AccountId(UUID.fromString("51c3d21f-21bf-49a4-8746-3d508b013db9")));
        account.setBalance(balance);

        return account;
    }
}
