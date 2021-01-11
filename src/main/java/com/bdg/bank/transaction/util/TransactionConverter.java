package com.bdg.bank.transaction.util;

import com.bdg.bank.transaction.dto.TransactionDto;
import com.bdg.bank.transaction.entity.Transaction;

import java.util.Set;
import java.util.stream.Collectors;

public class TransactionConverter {

    public static TransactionDto convertToTransactionDetails(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .transactionOperation(transaction.getTransactionOperation())
                .accountNumber(transaction.getAccountNumber())
                .date(transaction.getDate())
                .user_id(transaction.getOwner().getId())
                .amount(transaction.getAmount())
                .build();
    }


    public static Set<TransactionDto> convertToTransactionDetailsSet(Set<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionConverter::convertToTransactionDetails)
                .collect(Collectors.toSet());
    }

}
