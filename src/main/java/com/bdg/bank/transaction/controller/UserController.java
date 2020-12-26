package com.bdg.bank.transaction.controller;

import com.bdg.bank.transaction.domain.UserInfo;
import com.bdg.bank.transaction.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController("/users")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserInfo userInfo) {
        return userService.registerUser(userInfo);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id) {
        return userService.changeUserRole(id);
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<?> getTransactionHistory(@PathVariable Long id) {
        return userService.getTransactionHistory(id);
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<?> getFilteredTransactionHistory(@PathVariable Long id, @RequestParam LocalDate date) {
        return userService.getTransactionHistoryForSpecifiedDate(id, date);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
