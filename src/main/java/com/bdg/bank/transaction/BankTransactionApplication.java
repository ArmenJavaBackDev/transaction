package com.bdg.bank.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class BankTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTransactionApplication.class, args);
	}

}
