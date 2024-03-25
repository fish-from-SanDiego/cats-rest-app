package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;

import java.math.BigDecimal;

/**
 * The type Deposit charge strategy const.
 */
public class DepositChargeStrategyConstImpl implements DepositChargeStrategy {
    private final BigDecimal constPercent;

    /**
     * Instantiates a new Deposit charge strategy const.
     *
     * @param constPercent the const percent
     */
    public DepositChargeStrategyConstImpl(BigDecimal constPercent) {
        this.constPercent = constPercent;
    }

    @Override
    public BigDecimal getPercentByBalance(BigDecimal balance) {
        return constPercent;
    }
}
