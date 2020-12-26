package com.bdg.bank.transaction.service.impl;

import com.bdg.bank.transaction.domain.TransactionDetails;
import com.bdg.bank.transaction.domain.UserInfo;
import com.bdg.bank.transaction.entity.Authority;
import com.bdg.bank.transaction.entity.Roles;
import com.bdg.bank.transaction.entity.Transaction;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.AuthorityRepository;
import com.bdg.bank.transaction.repository.TransactionRepository;
import com.bdg.bank.transaction.repository.UserRepository;
import com.bdg.bank.transaction.service.IUserService;
import com.bdg.bank.transaction.util.TransactionConverter;
import com.bdg.bank.transaction.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    private AuthorityRepository authorityRepository;

    private TransactionRepository transactionRepository;

    @Transactional
    public ResponseEntity<?> registerUser(UserInfo userInfo) {
        Optional<UserEntity> existingUser = userRepository.findByUsername(userInfo.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("User with username %s already exist", userInfo.getUsername()));
        }
        Authority authority = authorityRepository.save(Authority.builder().role(Roles.USER).build());
        UserEntity user = UserConverter.convertToUserEntity(userInfo, authority);
        userRepository.save(user);
        return ResponseEntity.ok(user.getId());
    }

    public ResponseEntity<?> changeUserRole(Long id) {
        UserEntity user = userRepository.getOne(id);
        user.setAuthorities(Set.of(Authority.builder().role(Roles.ADMIN).build()));
        UserEntity savedUser = userRepository.save(user);
        UserInfo userInfo = UserConverter.convertToUserInfo(savedUser);
        return ResponseEntity.ok(userInfo);
    }

    public ResponseEntity<?> getTransactionHistory(Long userId) {
        UserEntity currentUser = userRepository.getOne(userId);
        Set<Transaction> transactions = currentUser.getTransactions();
        Set<TransactionDetails> transactionDetails = TransactionConverter.convertToTransactionDetailsSet(transactions);
        return ResponseEntity.ok(transactionDetails);
    }

    public ResponseEntity<?> getTransactionHistoryForSpecifiedDate(Long userId, LocalDate date) {
        UserEntity currentUser = userRepository.getOne(userId);
        Set<Transaction> transactions = transactionRepository.findByOwnerAndDate(currentUser, date);
        Set<TransactionDetails> transactionDetails = TransactionConverter.convertToTransactionDetailsSet(transactions);
        return ResponseEntity.ok(transactionDetails);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
