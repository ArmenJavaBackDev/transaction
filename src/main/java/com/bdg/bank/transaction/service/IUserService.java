package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {
    ResponseEntity<?> registerUser(UserDto userDto);

    ResponseEntity<?> changeUserRole(Long id);

    ResponseEntity<?> getTransactionHistory(Long userId);

    ResponseEntity<?> getTransactionHistoryForSpecifiedDate(Long userId, LocalDate date);

    List<UserDto> findAllUsers();
}
