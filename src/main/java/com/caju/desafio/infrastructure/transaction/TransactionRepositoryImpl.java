package com.caju.desafio.infrastructure.transaction;

import com.caju.desafio.domain.transactions.ITransactionRepository;
import com.caju.desafio.domain.transactions.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImpl implements ITransactionRepository {

    private final MongoTransactionRepository transactionRepository;

    public TransactionRepositoryImpl(MongoTransactionRepository mongoTransactionRepository) {
        this.transactionRepository = mongoTransactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
