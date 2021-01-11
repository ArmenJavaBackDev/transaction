package com.bdg.bank.transaction.controller;

import com.bdg.bank.transaction.dto.AccountDto;
import com.bdg.bank.transaction.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
