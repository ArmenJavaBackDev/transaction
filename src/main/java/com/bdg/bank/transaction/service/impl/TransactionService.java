package com.bdg.bank.transaction.service.impl;

import com.bdg.bank.transaction.domain.TransactionDetails;
import com.bdg.bank.transaction.entity.Account;
import com.bdg.bank.transaction.entity.Status;
import com.bdg.bank.transaction.entity.Transaction;
import com.bdg.bank.transaction.entity.TransactionOperation;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.AccountRepository;
import com.bdg.bank.transaction.repository.TransactionRepository;
import com.bdg.bank.transaction.repository.UserRepository;
import com.bdg.bank.transaction.security.AuthenticationFacade;
import com.bdg.bank.transaction.util.TransactionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    private AuthenticationFacade authenticationFacade;

    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> createTransaction(TransactionDetails transactionDetails) {
        TransactionOperation transactionOperation = transactionDetails.getTransactionOperation();
        switch (transactionOperation) {
            case DEPOSIT:
                return deposit(transactionDetails);
            case WITHDRAW:
                return withDraw(transactionDetails);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<Set<TransactionDetails>> findTransactionsWithPendingStatus(Long userId, LocalDate date) {
        UserEntity owner = userRepository.getOne(userId);
        Optional<Set<Transaction>> optionalTransactionList = transactionRepository.findByOwnerAndDateAndStatus(owner, date, Status.PENDING);
        if (optionalTransactionList.isPresent()) {
            Set<Transaction> transactions = optionalTransactionList.get();
            Set<TransactionDetails> pendingTransactions = transactions.stream()
                    .map(TransactionConverter::convertToTransactionDetails)
                    .collect(Collectors.toSet());
            return ResponseEntity.ok(pendingTransactions);
        }
        return ResponseEntity.badRequest().build();
    }

    private ResponseEntity<?> deposit(TransactionDetails transactionDetails) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(transactionDetails.getAccountNumber());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            Optional<UserEntity> optionalUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
            UserEntity user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User: %s not found"));
            Transaction transaction = TransactionConverter.convertToPendingTransaction(transactionDetails, user);
            account.setBalance(account.getBalance() + transaction.getAmount());
            accountRepository.save(account);
            transactionRepository.save(transaction);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    private ResponseEntity<?> withDraw(TransactionDetails transactionDetails) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(transactionDetails.getAccountNumber());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getBalance() - transactionDetails.getAmount() > 0) {
                Optional<UserEntity> optionalUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
                UserEntity user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User: %s not found"));
                Transaction transaction = TransactionConverter.convertToPendingTransaction(transactionDetails, user);
                account.setBalance(account.getBalance() - transaction.getAmount());
                accountRepository.save(account);
                transactionRepository.save(transaction);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }


    public ResponseEntity<TransactionDetails> acceptTransaction(Long id) {
        Transaction pendingTransaction = transactionRepository.getOne(id);
        pendingTransaction.setStatus(Status.ACCEPTED);
        transactionRepository.save(pendingTransaction);
        return ResponseEntity.ok(TransactionConverter.convertToTransactionDetails(pendingTransaction));
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setAuthenticationFacade(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
