package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.dto.TransactionDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    List<TransactionDto> findTransactionsWithPendingStatus(Long userId, LocalDate date);

    void acceptTransaction(Long id);

}
