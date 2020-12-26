package com.bdg.bank.transaction.security;

import com.bdg.bank.transaction.entity.Authority;
import com.bdg.bank.transaction.entity.Roles;
import com.bdg.bank.transaction.entity.UserEntity;
import com.bdg.bank.transaction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BankUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Get user via user repository");
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User name: %s not found", username)));
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEnabled(),
                userEntity.getAccountNonExpired(),
                userEntity.getCredentialsNonExpired(),
                userEntity.getAccountNonLocked(),
                convertToGrantedAuthorities(userEntity.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToGrantedAuthorities(Set<Authority> authorities) {
        if (authorities != null && authorities.size() > 0)
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(Roles::toString)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        return new HashSet<>();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
