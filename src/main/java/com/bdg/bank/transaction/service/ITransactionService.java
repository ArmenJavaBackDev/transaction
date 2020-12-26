package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.domain.TransactionDetails;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Set;

public interface ITransactionService {

    ResponseEntity<?> createTransaction(TransactionDetails transactionDetails);

    ResponseEntity<Set<TransactionDetails>> findTransactionsWithPendingStatus(Long userId, LocalDate date);

    ResponseEntity<TransactionDetails> acceptTransaction(Long id);

}
