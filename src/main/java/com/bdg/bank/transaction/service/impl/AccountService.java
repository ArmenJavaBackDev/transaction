package com.bdg.bank.transaction.service.impl;

import com.bdg.bank.transaction.dto.AccountDto;
import com.bdg.bank.transaction.entity.Account;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.AccountRepository;
import com.bdg.bank.transaction.repository.UserRepository;
import com.bdg.bank.transaction.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Transactional
    public ResponseEntity<?> createAccount(AccountDto accountDto) {
        Optional<Account> existingAccount = accountRepository.findByAccountNumber(accountDto.getAccountNumber());
        if (existingAccount.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(String.format("Account with account number %s already exist", accountDto.getAccountNumber()));
        }
        Optional<UserEntity> optionalUser = userRepository.findById(accountDto.getUserId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("User not found");
        }
        UserEntity owner = optionalUser.get();
        var account = modelMapper.map(accountDto,Account.class);
        account.setUser(owner);
        accountRepository.save(account);
        return ResponseEntity.ok().build();
    }
}
