package com.bdg.bank.transaction.controller;

import com.bdg.bank.transaction.dto.TransactionDto;
import com.bdg.bank.transaction.dto.UserDto;
import com.bdg.bank.transaction.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> allUsers = userService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        if (userService.isUserExist(userDto.getUsername())) {
            ResponseEntity.badRequest().body("User already exist");
        }
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<Void> changeUserRole(@PathVariable Long id) {
        userService.changeUserRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<Set<TransactionDto>> getTransactionHistory(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTransactionHistory(id));
    }

    @GetMapping("/{id}/transaction/filter")
    public ResponseEntity<Set<TransactionDto>> getFilteredTransactionHistory(@PathVariable Long id,
                                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(userService.getTransactionHistoryForSpecifiedDate(id, date));
    }
}
