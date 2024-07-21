package com.caju.desafio.presentation.transaction;

import com.caju.desafio.application.transactions.CreateTransactionRequest;
import com.caju.desafio.application.transactions.TransactionService;
import com.caju.desafio.presentation.ResponseRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/charge")
    public ResponseEntity<ResponseRecord> transact(@RequestBody CreateTransactionRequest request){
        var response = transactionService.chargeCard(request);

        return new ResponseEntity<>(new ResponseRecord(response.getError().code()), HttpStatus.OK);
    }
}
