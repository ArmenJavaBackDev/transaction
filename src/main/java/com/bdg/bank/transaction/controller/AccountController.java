package com.bdg.bank.transaction.controller;

import com.bdg.bank.transaction.dto.AccountDto;
import com.bdg.bank.transaction.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDto accountDto) {
        if (accountService.isAccountExist(accountDto.getAccountNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(String.format("Account with account number %s already exist", accountDto.getAccountNumber()));
        }
        return ResponseEntity.ok(accountService.createAccount(accountDto));
    }
}
