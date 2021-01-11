package com.bdg.bank.transaction.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "users.default")
@Getter
@Setter
public class DefaultUserProperties {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}
