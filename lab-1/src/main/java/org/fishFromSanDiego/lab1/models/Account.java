package org.fishFromSanDiego.lab1.models;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * The type Account.
 */
@Builder
public record Account(BigDecimal balance, AccountType accountType, int clientId) {
    /**
     * Direct builder account . account builder.
     *
     * @param builder the builder
     * @return the account . account builder
     */
    public Account.AccountBuilder directBuilder(Account.AccountBuilder builder) {
        return builder
                .balance(balance)
                .accountType(accountType)
                .clientId(clientId);
    }
}
