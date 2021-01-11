package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.dto.AccountDto;
import org.springframework.http.ResponseEntity;

public interface IAccountService {
    ResponseEntity<?> createAccount(AccountDto accountDto);
}
