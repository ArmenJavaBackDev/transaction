package com.bdg.bank.transaction.config;

import com.bdg.bank.transaction.util.ModelMapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankTransactionConfigBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapperHelper modelMapperHelper() {
        return new ModelMapperHelper(modelMapper());
    }
}
