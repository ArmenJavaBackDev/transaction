package com.bdg.bank.transaction.dto;

import com.bdg.bank.transaction.entity.TransactionOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto implements Serializable {
    private Long id;

    private Long user_id;

    @NotBlank(message = "Account number is mandatory")
    @Size(min = 12, max = 12, message = "Account number should be 12 digits")
    private String accountNumber;

    @NotBlank(message = "Transaction operation is missing, available options: DEPOSIT, WITHDRAW")
    private TransactionOperation transactionOperation;

    @NotBlank(message = "Amount is mandatory")
    @PositiveOrZero(message = "Amount should equal or greater than 0")
    private Double amount;

    private LocalDate date;
}
