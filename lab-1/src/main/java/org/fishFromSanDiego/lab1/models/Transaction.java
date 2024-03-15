package org.fishFromSanDiego.lab1.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
public abstract class Transaction {
    protected final BigDecimal sum;
    protected final boolean isReverted;

    protected Transaction(BigDecimal sum, boolean isReverted) {
        this.sum = sum;
        this.isReverted = isReverted;
    }

    public abstract String getTransactionTypeName();


    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Transfer extends Transaction {
        @Builder
        public Transfer(
                BigDecimal sum,
                boolean isReverted,
                int senderBankId,
                int recipientBankId,
                int senderId,
                int recipientId) {
            super(sum, isReverted);
            this.senderBankId = senderBankId;
            this.recipientBankId = recipientBankId;
            this.senderId = senderId;
            this.recipientId = recipientId;
        }

        int senderBankId;
        int recipientBankId;
        int senderId;
        int recipientId;

        public Transaction.Transfer.TransferBuilder directBuilder(Transaction.Transfer.TransferBuilder builder) {
            return builder
                    .isReverted(super.isReverted)
                    .sum(super.sum)
                    .recipientBankId(recipientBankId)
                    .recipientId(recipientId)
                    .senderBankId(senderBankId)
                    .senderId(senderId);

        }

        @Override
        public String getTransactionTypeName() {
            return "Transfer";
        }

    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Deposit extends Transaction {
        @Builder
        public Deposit(BigDecimal sum, boolean isReverted, int recipientBankId, int recipientId) {
            super(sum, isReverted);
            this.recipientBankId = recipientBankId;
            this.recipientId = recipientId;
        }

        int recipientBankId;
        int recipientId;

        public Transaction.Deposit.DepositBuilder directBuilder(Transaction.Deposit.DepositBuilder builder) {
            return builder
                    .isReverted(super.isReverted)
                    .sum(super.sum)
                    .recipientBankId(recipientBankId)
                    .recipientId(recipientId);

        }

        @Override
        public String getTransactionTypeName() {
            return "Deposit";
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Withdrawal extends Transaction {
        @Builder
        public Withdrawal(BigDecimal sum, boolean isReverted, int bankId, int accountId) {
            super(sum, isReverted);
            this.bankId = bankId;
            this.accountId = accountId;
        }

        int bankId;
        int accountId;

        public Transaction.Withdrawal.WithdrawalBuilder directBuilder(Transaction.Withdrawal.WithdrawalBuilder builder) {
            return builder
                    .isReverted(super.isReverted)
                    .sum(super.sum)
                    .bankId(bankId)
                    .accountId(accountId);

        }

        @Override
        public String getTransactionTypeName() {
            return "Withdrawal";
        }
    }
}
