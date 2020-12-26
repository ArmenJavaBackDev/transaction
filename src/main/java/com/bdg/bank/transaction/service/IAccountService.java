package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.domain.AccountDetails;
import org.springframework.http.ResponseEntity;

public interface IAccountService {
    ResponseEntity<?> createAccount(AccountDetails accountDetails);
}
