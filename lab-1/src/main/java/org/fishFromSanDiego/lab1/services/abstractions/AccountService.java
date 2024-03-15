package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.abstractions.Observer;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    void depositMoney(BigDecimal amount) throws RepositoryException;

    void withdrawMoney(BigDecimal amount) throws RepositoryException;

    void transferMoney(BigDecimal amount, int recipientBankId, int recipientId) throws RepositoryException;
}
