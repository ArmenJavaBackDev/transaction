package com.bdg.bank.transaction.controller;

import com.bdg.bank.transaction.dto.TransactionDto;
import com.bdg.bank.transaction.service.ITransactionService;
import com.bdg.bank.transaction.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> startTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TransactionDto>> getTransactionsWithPendingStatus(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return transactionService.findTransactionsWithPendingStatus(userId, date);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> acceptTransaction(@PathVariable Long id) {
        return transactionService.acceptTransaction(id);
    }
}
