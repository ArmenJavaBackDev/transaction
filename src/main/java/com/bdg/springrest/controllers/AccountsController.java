package com.bdg.springrest.controllers;

import com.bdg.springrest.form.AccountForm;
import com.bdg.springrest.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<Object> addAccount(@RequestBody AccountForm accountForm) {
        accountService.createAccount(accountForm);
        return ResponseEntity.ok().build();
    }

}
