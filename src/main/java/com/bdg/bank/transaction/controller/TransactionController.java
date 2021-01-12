package com.bdg.bank.transaction.controller;

import com.bdg.bank.transaction.dto.TransactionDto;
import com.bdg.bank.transaction.service.IAccountService;
import com.bdg.bank.transaction.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;
    private final IAccountService accountService;

    @PostMapping
    public ResponseEntity<?> startTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        return accountService.isAccountExist(transactionDto.getAccountNumber()) ?
                ResponseEntity.ok(transactionService.createTransaction(transactionDto)) :
                ResponseEntity.badRequest().body("Cannot start transaction");
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TransactionDto>> getTransactionsWithPendingStatus(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(transactionService.findTransactionsWithPendingStatus(userId, date));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> acceptTransaction(@PathVariable Long id) {
        transactionService.acceptTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
