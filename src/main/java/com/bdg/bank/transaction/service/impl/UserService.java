package com.bdg.bank.transaction.service.impl;

import com.bdg.bank.transaction.dto.TransactionDto;
import com.bdg.bank.transaction.dto.UserDto;
import com.bdg.bank.transaction.entity.Authority;
import com.bdg.bank.transaction.entity.Roles;
import com.bdg.bank.transaction.entity.Transaction;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.AuthorityRepository;
import com.bdg.bank.transaction.repository.TransactionRepository;
import com.bdg.bank.transaction.repository.UserRepository;
import com.bdg.bank.transaction.service.IUserService;
import com.bdg.bank.transaction.util.ModelMapperHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ModelMapperHelper modelMapperHelper;

    @Transactional
    public ResponseEntity<?> registerUser(UserDto userDto) {
        Optional<UserEntity> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("User with username '%s' already exist", userDto.getUsername()));
        }
        Authority authority = authorityRepository.findByRole(Roles.USER);
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        user.setAuthorities(Set.of(authority));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        UserDto createdUser = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(createdUser.getId());
    }

    @Transactional
    public ResponseEntity<?> changeUserRole(Long id) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            UserDto updatedUser = updateAuthorities(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> getTransactionHistory(Long userId) {
        UserEntity currentUser = userRepository.getOne(userId);
        Set<Transaction> transactions = currentUser.getTransactions();
        Set<TransactionDto> transactionDetails = modelMapperHelper.mapSet(transactions, TransactionDto.class);
        return ResponseEntity.ok(transactionDetails);
    }

    public ResponseEntity<?> getTransactionHistoryForSpecifiedDate(Long userId, LocalDate date) {
        UserEntity currentUser = userRepository.getOne(userId);
        Set<Transaction> transactions = transactionRepository.findByOwnerAndDate(currentUser, date);
        Set<TransactionDto> transactionDetails = modelMapperHelper.mapSet(transactions, TransactionDto.class);
        return ResponseEntity.ok(transactionDetails);
    }

    private UserDto updateAuthorities(UserEntity userEntity) {
        Authority admin = authorityRepository.findByRole(Roles.ADMIN);
        Authority user = authorityRepository.findByRole(Roles.USER);
        userEntity.getAuthorities().remove(user);
        user.getUserEntities().remove(userEntity);
        userEntity.getAuthorities().add(admin);
        admin.getUserEntities().add(userEntity);
        userRepository.save(userEntity);
        authorityRepository.save(user);
        authorityRepository.save(admin);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> allUsers = userRepository.findAll();
        return modelMapperHelper.mapList(allUsers, UserDto.class);
    }
}
