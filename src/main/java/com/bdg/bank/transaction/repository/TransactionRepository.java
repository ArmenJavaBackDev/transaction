package com.bdg.bank.transaction.repository;

import com.bdg.bank.transaction.entity.Status;
import com.bdg.bank.transaction.entity.Transaction;
import com.bdg.bank.transaction.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Set<Transaction>> findByOwnerAndDateAndStatus(UserEntity user, LocalDate date, Status status);

    Set<Transaction> findByOwnerAndDate(UserEntity user, LocalDate date);
}
