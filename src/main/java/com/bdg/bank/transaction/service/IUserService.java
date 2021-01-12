package com.bdg.bank.transaction.service;

import com.bdg.bank.transaction.dto.TransactionDto;
import com.bdg.bank.transaction.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IUserService {
    UserDto registerUser(UserDto userDto);

    void changeUserRole(Long id);

    Set<TransactionDto> getTransactionHistory(Long userId);

    Set<TransactionDto> getTransactionHistoryForSpecifiedDate(Long userId, LocalDate date);

    List<UserDto> findAllUsers();

    boolean isUserExist(String userName);
}
