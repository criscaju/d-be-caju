package com.caju.desafio.infrastructure.transaction;

import com.caju.desafio.domain.transactions.Transaction;
import com.caju.desafio.domain.transactions.TransactionId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoTransactionRepository extends MongoRepository<Transaction, TransactionId> {
}
