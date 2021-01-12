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
    public UserDto registerUser(UserDto userDto) {
        Authority authority = authorityRepository.findByRole(Roles.USER);
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        user.setAuthorities(Set.of(authority));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public void changeUserRole(Long id) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        existingUser.ifPresent(this::updateAuthorities);
    }

    public Set<TransactionDto> getTransactionHistory(Long userId) {
        UserEntity currentUser = userRepository.getOne(userId);
        Set<Transaction> transactions = currentUser.getTransactions();
        return modelMapperHelper.mapSet(transactions, TransactionDto.class);
    }

    public Set<TransactionDto> getTransactionHistoryForSpecifiedDate(Long userId, LocalDate date) {
        UserEntity currentUser = userRepository.getOne(userId);
        Set<Transaction> transactions = transactionRepository.findByOwnerAndDate(currentUser, date);
        return modelMapperHelper.mapSet(transactions, TransactionDto.class);
    }

    private void updateAuthorities(UserEntity userEntity) {
        Authority admin = authorityRepository.findByRole(Roles.ADMIN);
        Authority user = authorityRepository.findByRole(Roles.USER);
        userEntity.getAuthorities().remove(user);
        user.getUserEntities().remove(userEntity);
        userEntity.getAuthorities().add(admin);
        admin.getUserEntities().add(userEntity);
        userRepository.save(userEntity);
        authorityRepository.save(user);
        authorityRepository.save(admin);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> allUsers = userRepository.findAll();
        return modelMapperHelper.mapList(allUsers, UserDto.class);
    }

    public boolean isUserExist(String userName) {
        Optional<UserEntity> user = userRepository.findByUsername(userName);
        return user.isPresent();
    }
}
