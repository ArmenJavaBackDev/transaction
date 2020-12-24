package com.bdg.springrest.repositories;

import com.bdg.springrest.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account, Long> {
}
