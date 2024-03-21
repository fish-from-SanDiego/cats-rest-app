package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;

import java.math.BigDecimal;

public class DepositChargeStrategyConstImpl implements DepositChargeStrategy {
    private final BigDecimal constPercent;

    public DepositChargeStrategyConstImpl(BigDecimal constPercent) {
        this.constPercent = constPercent;
    }

    @Override
    public BigDecimal getPercentByBalance(BigDecimal balance) {
        return constPercent;
    }
}
