package com.bdg.bank.transaction.service.impl;

import com.bdg.bank.transaction.dto.TransactionDto;
import com.bdg.bank.transaction.entity.Account;
import com.bdg.bank.transaction.entity.Status;
import com.bdg.bank.transaction.entity.Transaction;
import com.bdg.bank.transaction.entity.TransactionOperation;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.AccountRepository;
import com.bdg.bank.transaction.repository.TransactionRepository;
import com.bdg.bank.transaction.repository.UserRepository;
import com.bdg.bank.transaction.security.impl.AuthenticationFacade;
import com.bdg.bank.transaction.service.ITransactionService;
import com.bdg.bank.transaction.util.ModelMapperHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ModelMapperHelper modelMapperHelper;

    public List<TransactionDto> findTransactionsWithPendingStatus(Long userId, LocalDate date) {
        UserEntity owner = userRepository.getOne(userId);
        Optional<List<Transaction>> optionalTransactionList = transactionRepository.findByOwnerAndDateAndStatus(owner, date, Status.PENDING);
        return optionalTransactionList.map(transactions -> modelMapperHelper.mapList(transactions, TransactionDto.class))
                .orElse(new ArrayList<>());
    }

    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        optionalUser.ifPresent(transaction::setOwner);
        transaction.setDate(LocalDate.now());
        transaction.setStatus(Status.PENDING);
        transactionRepository.save(transaction);
        return modelMapper.map(transaction, TransactionDto.class);

    }

    @Transactional
    public void acceptTransaction(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction pendingTransaction = optionalTransaction.get();
            pendingTransaction.setStatus(Status.ACCEPTED);
            makeMoneyTransfer(pendingTransaction);
        }
    }

    private void makeMoneyTransfer(Transaction transaction) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(transaction.getAccountNumber());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            doTransfer(transaction, account);
        }
    }

    private void doTransfer(Transaction transaction, Account account) {
        TransactionOperation transactionOperation = transaction.getTransactionOperation();
        switch (transactionOperation) {
            case DEPOSIT:
                deposit(transaction, account);
                break;
            case WITHDRAW:
                withDraw(transaction, account);
                break;
        }
        accountRepository.save(account);
    }


    private void deposit(Transaction transaction, Account account) {
        account.setBalance(account.getBalance() + transaction.getAmount());
    }

    private void withDraw(Transaction transaction, Account account) {
        account.setBalance(account.getBalance() - transaction.getAmount());
    }

}
