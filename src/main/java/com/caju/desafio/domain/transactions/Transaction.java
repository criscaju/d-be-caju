package com.caju.desafio.domain.transactions;

import com.caju.desafio.domain.accounts.AccountId;
import com.caju.desafio.domain.merchants.Mcc;
import com.caju.desafio.domain.merchants.MerchantName;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("transactions")
public class Transaction {

    private TransactionId id;
    private AccountId accountId;
    private Amount amount;
    private MerchantName merchant;
    private Mcc mcc;

    public TransactionId getId() {
        return id;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public Amount getAmount() {
        return amount;
    }

    public MerchantName getMerchant() {
        return merchant;
    }

    public Mcc getMcc() {
        return mcc;
    }

    private void setId(TransactionId id) {
        this.id = id;
    }

    private void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    private void setAmount(Amount amount) {
        this.amount = amount;
    }

    private void setMerchant(MerchantName merchant) {
        this.merchant = merchant;
    }

    private void setMcc(Mcc mcc) {
        this.mcc = mcc;
    }

    private Transaction(){

    }

    public static Transaction createTransaction(AccountId accountId, Amount amount, MerchantName merchant, Mcc mcc){
        Transaction t = new Transaction();
        t.setId(new TransactionId());
        t.setAccountId(accountId);
        t.setAmount(amount);
        t.setMerchant(merchant);
        t.setMcc(mcc);
        return t;
    }
}
