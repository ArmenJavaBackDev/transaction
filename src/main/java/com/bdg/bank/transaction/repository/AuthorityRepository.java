package com.bdg.bank.transaction.repository;

import com.bdg.bank.transaction.entity.Authority;
import com.bdg.bank.transaction.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByRole(Roles role);

    Authority getAuthorityByRole(Roles role);
}
