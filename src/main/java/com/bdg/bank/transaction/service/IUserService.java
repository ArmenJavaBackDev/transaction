package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.domain.UserInfo;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface IUserService {
    ResponseEntity<?> registerUser(UserInfo userInfo);

    ResponseEntity<?> changeUserRole(Long id);

    ResponseEntity<?> getTransactionHistory(Long userId);

    ResponseEntity<?> getTransactionHistoryForSpecifiedDate(Long userId, LocalDate date);
}
