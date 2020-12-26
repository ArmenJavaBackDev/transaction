package com.bdg.bank.transaction.controller;

import com.bdg.bank.transaction.domain.TransactionDetails;
import com.bdg.bank.transaction.service.impl.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> startTransaction(@RequestBody TransactionDetails transactionDetails) {
        return transactionService.createTransaction(transactionDetails);
    }

    @GetMapping("/filter")
    public ResponseEntity<Set<TransactionDetails>> getTransactionsWithPendingStatus(@RequestParam Long userId, @RequestParam LocalDate date) {
        return transactionService.findTransactionsWithPendingStatus(userId, date);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> acceptTransaction(@PathVariable Long id) {
        return transactionService.acceptTransaction(id);
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
