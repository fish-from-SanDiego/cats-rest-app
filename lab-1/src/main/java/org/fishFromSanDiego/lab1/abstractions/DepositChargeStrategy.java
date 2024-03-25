package org.fishFromSanDiego.lab1.abstractions;

import java.math.BigDecimal;

/**
 * The interface Deposit charge strategy.
 */
public interface DepositChargeStrategy {
    /**
     * Gets percent by balance.
     *
     * @param balance the account balance
     * @return the percent to charge
     */
    BigDecimal getPercentByBalance(BigDecimal balance);
}
