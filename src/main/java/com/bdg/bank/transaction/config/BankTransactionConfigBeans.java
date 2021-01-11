package com.bdg.bank.transaction.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankTransactionConfigBeans {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
