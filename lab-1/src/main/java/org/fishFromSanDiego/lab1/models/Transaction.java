package org.fishFromSanDiego.lab1.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

/**
 * The type Transaction.
 */
@Getter
public abstract class Transaction {
    /**
     * The Sum.
     */
    protected final BigDecimal sum;
    /**
     * The Can be reverted.
     */
    protected final boolean canBeReverted;


    /**
     * Instantiates a new Transaction.
     *
     * @param sum           the sum
     * @param canBeReverted the can be reverted
     */
    protected Transaction(BigDecimal sum, boolean canBeReverted) {
        this.sum = sum;
        this.canBeReverted = canBeReverted;
    }

    /**
     * Gets transaction type name.
     *
     * @return the transaction type name
     */
    public abstract String getTransactionTypeName();


    /**
     * The type Transfer.
     */
    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Transfer extends Transaction {
        @Builder
        public Transfer(
                BigDecimal sum,
                boolean canBeReverted,
                int senderBankId,
                int recipientBankId,
                int senderId,
                int recipientId) {
            super(sum, canBeReverted);
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
                    .canBeReverted(super.canBeReverted)
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

    /**
     * The type Deposit.
     */
    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Deposit extends Transaction {
        @Builder
        public Deposit(BigDecimal sum, boolean canBeReverted, int recipientBankId, int recipientId) {
            super(sum, canBeReverted);
            this.recipientBankId = recipientBankId;
            this.recipientId = recipientId;
        }

        int recipientBankId;
        int recipientId;

        public Transaction.Deposit.DepositBuilder directBuilder(Transaction.Deposit.DepositBuilder builder) {
            return builder
                    .canBeReverted(super.canBeReverted)
                    .sum(super.sum)
                    .recipientBankId(recipientBankId)
                    .recipientId(recipientId);

        }

        @Override
        public String getTransactionTypeName() {
            return "Deposit";
        }
    }

    /**
     * The type Withdrawal.
     */
    @EqualsAndHashCode(callSuper = true)
    @Value
    public static class Withdrawal extends Transaction {
        @Builder
        public Withdrawal(BigDecimal sum, boolean canBeReverted, int bankId, int accountId) {
            super(sum, canBeReverted);
            this.bankId = bankId;
            this.accountId = accountId;
        }

        int bankId;
        int accountId;

        public Transaction.Withdrawal.WithdrawalBuilder directBuilder(Transaction.Withdrawal.WithdrawalBuilder builder) {
            return builder
                    .canBeReverted(super.canBeReverted)
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
