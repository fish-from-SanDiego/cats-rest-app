package org.fishFromSanDiego.lab1.abstractions;

import java.math.BigDecimal;

public interface DepositChargeStrategy {
    BigDecimal getPercentByBalance(BigDecimal balance);
}
