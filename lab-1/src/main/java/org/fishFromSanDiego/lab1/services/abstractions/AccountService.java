package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.math.BigDecimal;

public interface AccountService {
    FetchedModel<Account> getAccount() throws ServiceException;

    void depositMoney(BigDecimal amount) throws ServiceException;

    void withdrawMoney(BigDecimal amount) throws ServiceException;

    void transferMoney(BigDecimal amount, int recipientBankId, int recipientId) throws ServiceException;
}
