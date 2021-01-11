package com.bdg.bank.transaction.dto;

import com.bdg.bank.transaction.entity.TransactionOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto implements Serializable {
    private Long id;
    private Long user_id;
    private String accountNumber;
    private TransactionOperation transactionOperation;
    private Double amount;
    private LocalDate date;
}
