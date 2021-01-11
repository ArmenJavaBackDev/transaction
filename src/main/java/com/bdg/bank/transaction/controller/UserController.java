package com.bdg.bank.transaction.controller;

import com.bdg.bank.transaction.dto.UserDto;
import com.bdg.bank.transaction.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDto> allUsers = userService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id) {
        return userService.changeUserRole(id);
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<?> getTransactionHistory(@PathVariable Long id) {
        return userService.getTransactionHistory(id);
    }

    @GetMapping("/{id}/transaction/filter")
    public ResponseEntity<?> getFilteredTransactionHistory(@PathVariable Long id,
                                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return userService.getTransactionHistoryForSpecifiedDate(id, date);
    }
}
