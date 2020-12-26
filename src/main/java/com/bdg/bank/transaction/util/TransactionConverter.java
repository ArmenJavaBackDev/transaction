package com.bdg.bank.transaction.util;

import com.bdg.bank.transaction.domain.TransactionDetails;
import com.bdg.bank.transaction.entity.Status;
import com.bdg.bank.transaction.entity.Transaction;
import com.bdg.bank.transaction.entity.UserEntity;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionConverter {

    public static TransactionDetails convertToTransactionDetails(Transaction transaction) {
        return TransactionDetails.builder()
                .id(transaction.getId())
                .transactionOperation(transaction.getTransactionOperation())
                .date(transaction.getDate())
                .user_id(transaction.getOwner().getId())
                .amount(transaction.getAmount())
                .build();
    }

    public static Transaction convertToPendingTransaction(TransactionDetails transactionDetails, UserEntity user) {
        return Transaction.builder()
                .transactionOperation(transactionDetails.getTransactionOperation())
                .amount(transactionDetails.getAmount())
                .accountNumber(transactionDetails.getAccountNumber())
                .date(LocalDate.now())
                .status(Status.PENDING)
                .owner(user)
                .build();
    }

    public static Set<TransactionDetails> convertToTransactionDetailsSet(Set<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionConverter::convertToTransactionDetails)
                .collect(Collectors.toSet());
    }

}
