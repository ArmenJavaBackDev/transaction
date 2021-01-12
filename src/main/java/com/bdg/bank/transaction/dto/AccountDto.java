package com.bdg.bank.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {

    @NotBlank(message = "Account number is Mandatory")
    @Size(min = 12, max = 12, message = "Account number should be 12 digits")
    private String accountNumber;

    private Double balance;
    private Long userId;
}