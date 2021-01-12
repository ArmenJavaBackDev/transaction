package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.dto.AccountDto;

public interface IAccountService {
    AccountDto createAccount(AccountDto accountDto);

    boolean isAccountExist(String accountNumber);
}
