package com.bdg.bank.transaction.service.impl;

import com.bdg.bank.transaction.dto.AccountDto;
import com.bdg.bank.transaction.entity.Account;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.AccountRepository;
import com.bdg.bank.transaction.repository.UserRepository;
import com.bdg.bank.transaction.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public AccountDto createAccount(AccountDto accountDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(accountDto.getUserId());
        Account account = modelMapper.map(accountDto, Account.class);
        optionalUser.ifPresent(account::setUser);
        accountRepository.save(account);
        return modelMapper.map(account, AccountDto.class);
    }

    public boolean isAccountExist(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        return account.isPresent();
    }
}
