package org.fishFromSanDiego.lab1.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
public abstract class TransactionInfo {
    protected final BigDecimal sum;
    protected final boolean isReverted;

    protected TransactionInfo(BigDecimal sum, boolean isReverted) {
        this.sum = sum;
        this.isReverted = isReverted;
    }

    public abstract String getTransactionTypeName();


    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Transfer extends TransactionInfo {
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

        @Override
        public String getTransactionTypeName() {
            return "Transfer";
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Deposit extends TransactionInfo {
        @Builder
        public Deposit(BigDecimal sum, boolean isReverted, int recipientBankId, int recipientId) {
            super(sum, isReverted);
            this.recipientBankId = recipientBankId;
            this.recipientId = recipientId;
        }

        int recipientBankId;
        int recipientId;

        @Override
        public String getTransactionTypeName() {
            return "Deposit";
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Withdrawal extends TransactionInfo {
        @Builder
        public Withdrawal(BigDecimal sum, boolean isReverted, int bankId, int clientId) {
            super(sum, isReverted);
            this.bankId = bankId;
            this.clientId = clientId;
        }

        int bankId;
        int clientId;

        @Override
        public String getTransactionTypeName() {
            return "Withdrawal";
        }
    }
}
