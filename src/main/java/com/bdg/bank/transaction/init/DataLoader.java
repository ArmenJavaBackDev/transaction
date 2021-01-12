package com.bdg.bank.transaction.init;

import com.bdg.bank.transaction.config.DefaultUserProperties;
import com.bdg.bank.transaction.entity.Authority;
import com.bdg.bank.transaction.entity.Roles;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.AuthorityRepository;
import com.bdg.bank.transaction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.bdg.bank.transaction.entity.Roles.ADMIN;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DefaultUserProperties defaultUserProperties;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createAuthorityIfNotFound(ADMIN);
        createAuthorityIfNotFound(Roles.USER);
        saveAdminUser();
    }

    private void saveAdminUser() {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(defaultUserProperties.getUsername());
        if (optionalUserEntity.isEmpty()) {
            Authority authority = authorityRepository.getAuthorityByRole(Roles.valueOf(defaultUserProperties.getRole()));
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(defaultUserProperties.getUsername());
            userEntity.setPassword(passwordEncoder.encode(defaultUserProperties.getPassword()));
            userEntity.setFirstName(defaultUserProperties.getFirstName());
            userEntity.setLastName(defaultUserProperties.getLastName());
            userEntity.setAuthorities(Set.of(authority));
            userRepository.save(userEntity);
        }
    }

    @Transactional
    Authority createAuthorityIfNotFound(Roles role) {
        Authority authority = authorityRepository.findByRole(role);
        if (Objects.isNull(authority)) {
            authority = new Authority();
            authority.setRole(role);
            return authorityRepository.save(authority);
        }
        return authority;
    }
}
