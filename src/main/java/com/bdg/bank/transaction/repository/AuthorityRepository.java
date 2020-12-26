package com.bdg.bank.transaction.repository;

import com.bdg.bank.transaction.entity.Authority;
import com.bdg.bank.transaction.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByRole(Roles role);

    Authority getAuthorityByRole(Roles role);
}
