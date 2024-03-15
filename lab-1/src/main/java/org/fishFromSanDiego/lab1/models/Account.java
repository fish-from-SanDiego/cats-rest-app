package org.fishFromSanDiego.lab1.models;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Account(BigDecimal balance, AccountType accountType, int clientId) {
    public Account.AccountBuilder directBuilder(Account.AccountBuilder builder) {
        return builder
                .balance(balance)
                .accountType(accountType)
                .clientId(clientId);
    }
}
