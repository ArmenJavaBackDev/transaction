package com.bdg.bank.transaction.security;

import com.bdg.bank.transaction.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    BankUserDetailsService bankUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> {
            authorize
                    .antMatchers("/api/account").hasRole(Roles.ADMIN.toString())
                    .antMatchers(HttpMethod.PUT, "/api/users/**").hasRole(Roles.ADMIN.toString())
                    .antMatchers(HttpMethod.PATCH, "/api/transaction/**").hasRole(Roles.ADMIN.toString())
                    .antMatchers(HttpMethod.GET, "/api/user/**").hasRole(Roles.USER.toString())
                    .antMatchers(HttpMethod.GET, "/api/transaction/**").hasRole(Roles.USER.toString())
                    .antMatchers(HttpMethod.POST, "/api/transaction/**").hasRole(Roles.USER.toString())
                    .anyRequest().authenticated();
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(bankUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void setBankUserDetailsService(BankUserDetailsService bankUserDetailsService) {
        this.bankUserDetailsService = bankUserDetailsService;
    }
}
