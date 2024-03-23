package org.fishFromSanDiego.lab1.models;

import lombok.Builder;
import lombok.NonNull;
import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;

import java.math.BigDecimal;

/**
 * The type Bank.
 */
@Builder
public record Bank(@NonNull String name,
                   @NonNull BigDecimal suspiciousClientDepositLimit,
                   @NonNull BigDecimal suspiciousClientWithdrawalLimit,
                   @NonNull BigDecimal debitCardPercent,
                   @NonNull BigDecimal creditCardLimit,
                   @NonNull BigDecimal creditCardCommission,
                   @NonNull DepositChargeStrategy depositChargeStrategy) {

    /**
     * Direct builder bank . bank builder.
     *
     * @param builder the builder
     * @return the bank . bank builder
     */
    public Bank.BankBuilder directBuilder(Bank.BankBuilder builder) {
        return builder
                .creditCardCommission(creditCardCommission)
                .name(name)
                .creditCardLimit(creditCardLimit)
                .debitCardPercent(debitCardPercent)
                .depositChargeStrategy(depositChargeStrategy)
                .suspiciousClientDepositLimit(suspiciousClientDepositLimit)
                .suspiciousClientWithdrawalLimit(suspiciousClientWithdrawalLimit);
    }

    /**
     * The type Bank builder.
     */
    public static class BankBuilder {
    }
}
