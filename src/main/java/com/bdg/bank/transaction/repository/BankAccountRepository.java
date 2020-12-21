package com.bdg.bank.transaction.repository;

import com.bdg.bank.transaction.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
