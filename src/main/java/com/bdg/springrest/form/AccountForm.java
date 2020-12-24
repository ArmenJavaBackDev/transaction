package com.bdg.springrest.form;

import com.bdg.springrest.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountForm {
    private double balance;
    private String createDate;
    private Transaction transaction;
    private Long id;
}
