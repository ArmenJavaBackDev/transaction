package com.bdg.bank.transaction.util;

import com.bdg.bank.transaction.domain.AccountDetails;
import com.bdg.bank.transaction.entity.Account;
import com.bdg.bank.transaction.entity.UserEntity;

public class AccountConverter {
    public static Account convertToAccount(AccountDetails details, UserEntity user) {
        return Account.builder()
                .accountNumber(details.getAccountNumber())
                .balance(details.getBalance())
                .user(user)
                .build();
    }
}
