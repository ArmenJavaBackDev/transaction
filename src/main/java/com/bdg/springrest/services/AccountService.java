package com.bdg.springrest.services;

import com.bdg.springrest.form.AccountForm;

public interface AccountService {
    void createAccount(AccountForm accountForm);
    void deposit(double amount);
    void withdrawal(double amount);
    void getTransaction();
}
