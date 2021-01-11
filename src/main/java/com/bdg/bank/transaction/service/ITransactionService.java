package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.dto.TransactionDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ITransactionService {

    ResponseEntity<?> createTransaction(TransactionDto transactionDto);

    ResponseEntity<List<TransactionDto>> findTransactionsWithPendingStatus(Long userId, LocalDate date);

    ResponseEntity<TransactionDto> acceptTransaction(Long id);

}
