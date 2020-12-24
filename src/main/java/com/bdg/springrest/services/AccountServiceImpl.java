package com.bdg.springrest.services;

import com.bdg.springrest.form.AccountForm;
import com.bdg.springrest.models.Account;
import com.bdg.springrest.models.User;
import com.bdg.springrest.repositories.AccountsRepository;
import com.bdg.springrest.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void createAccount(AccountForm accountForm) {
        Optional<User> userCandidate = usersRepository.findById(accountForm.getId());
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();

            if (user.getId().equals(accountForm.getId())) {
                Account account = Account.builder()
                        .balance(accountForm.getBalance())
                        .createDate(LocalDate.now())
                        .transaction(accountForm.getTransaction())
                        .user(user)
                        .build();

                accountsRepository.save(account);
            }
        }
    }

    @Override
    public void deposit(double amount) {

    }

    @Override
    public void withdrawal(double amount) {

    }

    @Override
    public void getTransaction() {

    }
}
