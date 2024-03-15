package org.fishFromSanDiego.lab1.models;

import java.math.BigDecimal;

public record Account(BigDecimal balance, AccountType accountType) {
}
