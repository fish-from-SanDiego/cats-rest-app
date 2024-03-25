package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.math.BigDecimal;

/**
 * The interface Account service.
 */
public interface AccountService {
    /**
     * Gets account.
     *
     * @return the account
     * @throws ServiceException the service exception
     */
    FetchedModel<Account> getAccount() throws ServiceException;

    /**
     * Deposit money.
     *
     * @param amount the amount
     * @throws ServiceException the service exception
     */
    void depositMoney(BigDecimal amount) throws ServiceException;

    /**
     * Withdraw money.
     *
     * @param amount the amount
     * @throws ServiceException the service exception
     */
    void withdrawMoney(BigDecimal amount) throws ServiceException;

    /**
     * Transfer money.
     *
     * @param amount          the amount
     * @param recipientBankId the recipient bank id
     * @param recipientId     the recipient id
     * @throws ServiceException the service exception
     */
    void transferMoney(BigDecimal amount, int recipientBankId, int recipientId) throws ServiceException;
}
