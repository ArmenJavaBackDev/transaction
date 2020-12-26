package com.bdg.bank.transaction.service.impl;

import com.bdg.bank.transaction.domain.AccountDetails;
import com.bdg.bank.transaction.entity.Account;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.AccountRepository;
import com.bdg.bank.transaction.repository.UserRepository;
import com.bdg.bank.transaction.service.IAccountService;
import com.bdg.bank.transaction.util.AccountConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    private AccountRepository accountRepository;

    private UserRepository userRepository;


    @Transactional
    public ResponseEntity<?> createAccount(AccountDetails accountDetails) {
        Optional<Account> existingAccount = accountRepository.findByAccountNumber(accountDetails.getAccountNumber());
        if (existingAccount.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(String.format("Account with account number %s already exist", accountDetails.getAccountNumber()));
        }
        UserEntity owner = userRepository.getOne(accountDetails.getUserId());
        return ResponseEntity.ok(AccountConverter.convertToAccount(accountDetails, owner));
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
