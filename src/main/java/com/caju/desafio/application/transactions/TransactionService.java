package com.caju.desafio.application.transactions;

import com.caju.desafio.domain.accounts.AccountErrors;
import com.caju.desafio.domain.accounts.AccountId;
import com.caju.desafio.domain.accounts.BalanceType;
import com.caju.desafio.domain.accounts.IAccountRepository;
import com.caju.desafio.domain.merchants.IMerchantRepository;
import com.caju.desafio.domain.merchants.Mcc;
import com.caju.desafio.domain.merchants.Merchant;
import com.caju.desafio.domain.merchants.MerchantName;
import com.caju.desafio.domain.transactions.ITransactionRepository;
import com.caju.desafio.domain.transactions.TransactionErrors;
import com.caju.desafio.domain.transactions.Amount;
import com.caju.desafio.domain.transactions.Transaction;
import com.caju.desafio.shared.Result;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());

    private final IAccountRepository accountRepository;
    private final ITransactionRepository transactionRepository;
    private final IMerchantRepository merchantRepository;

    public TransactionService(IAccountRepository accountRepository, ITransactionRepository transactionRepository,
                              IMerchantRepository merchantRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
    }

    private Result validateTransaction(CreateTransactionRequest request) {

        if(request.accountId() == null)
            return Result.failure(TransactionErrors.INVALID_ACCOUNT_ID);

        if(request.mcc() == null || request.mcc().isEmpty())
            return Result.failure(TransactionErrors.INVALID_MCC);

        if(request.merchant() == null || request.merchant().isEmpty())
            return Result.failure(TransactionErrors.INVALID_MERCHANT);

        if(request.totalAmount() == null || request.totalAmount() <= 0d)
            return Result.failure(TransactionErrors.INVALID_AMOUNT);

        return Result.success();
    }

    public Result chargeCard(CreateTransactionRequest transactionRequest){
        var r = validateTransaction(transactionRequest);
        if(!r.getSuccess()) {
            LOGGER.log(Level.INFO,String.format("Invalid transaction request: %s", r.getError().description()));
            return r;
        }

        //L3 - Find correct MCC based on merchant name
        String mcc = verifyMccFromMerchant(new MerchantName(transactionRequest.merchant()), transactionRequest.mcc());

        var transaction = Transaction.createTransaction(
                new AccountId(transactionRequest.accountId()),
                new Amount(transactionRequest.totalAmount()),
                new MerchantName(transactionRequest.merchant()),
                new Mcc(mcc));
        transactionRepository.save(transaction);

        var account = accountRepository.findById(transaction.getAccountId());
        if(account == null) {
            LOGGER.log(Level.INFO, String.format("Transaction %s was not processed as account %s was not found", transaction.getId().value(), transaction.getAccountId().value()));
            return Result.failure(AccountErrors.NOT_FOUND);
        }

        //L1 - Debit from MCC
        var mccTry = account.debitFromBalance(getBalanceTypeByMcc(transaction.getMcc().value()), transaction.getAmount());

        if(mccTry.getSuccess()) {
            accountRepository.save(account);
            LOGGER.log(Level.INFO, String.format("L1 - Transaction %s processed successfully", transaction.getId().value()));
            return Result.success();
        }

        //L2 - Fallback to CASH balance
        if(getBalanceTypeByMcc(transaction.getMcc().value()) != BalanceType.CASH) {
            LOGGER.log(Level.INFO, String.format("L2 - Transaction %s was not processed due to insufficient funds, fallback to CASH", transaction.getId().value()));

            var cashTry = account.debitFromCashBalance(transaction.getAmount());
            if (cashTry.getSuccess()) {
                accountRepository.save(account);
                LOGGER.log(Level.INFO, String.format("L2 - Transaction %s processed successfully after fallback to CASH", transaction.getId().value()));
                return Result.success();
            }
        }

        LOGGER.log(Level.INFO, String.format("There are no funds for transaction %s", transaction.getId().value()));
        return Result.failure(AccountErrors.INSUFFICIENT_FUNDS);
    }

    private String verifyMccFromMerchant(MerchantName merchantName, String transactionMcc) {
        Merchant merchant = merchantRepository.findByName(merchantName);
        if(merchant != null && !merchant.getMcc().value().equals(transactionMcc)) {
            LOGGER.log(Level.INFO, "L3 - Found mismatch between merchant and transaction mcc, considering merchant mcc");
            return merchant.getMcc().value();
        }
        return transactionMcc;
    }

    private BalanceType getBalanceTypeByMcc(String mcc) {
        return switch (mcc) {
            case "5411", "5412" -> BalanceType.FOOD;
            case "5811", "5812" -> BalanceType.MEAL;
            default -> BalanceType.CASH;
        };
    }
}
