package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.abstractions.Publisher;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.Transaction;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * The interface Bank service.
 */
public interface BankService extends Publisher<String> {
    /**
     * Register new client int.
     *
     * @param newClient the new client
     * @param password  the password
     * @return the int
     * @throws ServiceException the service exception
     */
    int registerNewClient(Client newClient, String password) throws ServiceException;

    /**
     * Gets bank.
     *
     * @return the bank
     * @throws ServiceException the service exception
     */
    FetchedModel<Bank> getBank() throws ServiceException;

    /**
     * Charge all percents.
     *
     * @throws ServiceException the service exception
     */
    void chargeAllPercents() throws ServiceException;

    /**
     * Pay all percents.
     *
     * @throws ServiceException the service exception
     */
    void payAllPercents() throws ServiceException;

    /**
     * Take all commissions.
     *
     * @throws ServiceException the service exception
     */
    void takeAllCommissions() throws ServiceException;

    /**
     * Sets suspicious client deposit limit.
     *
     * @param newLimit the new limit
     * @throws ServiceException the service exception
     */
    void setSuspiciousClientDepositLimit(BigDecimal newLimit) throws ServiceException;

    /**
     * Sets suspicious client withdrawal limit.
     *
     * @param newLimit the new limit
     * @throws ServiceException the service exception
     */
    void setSuspiciousClientWithdrawalLimit(BigDecimal newLimit) throws ServiceException;

    /**
     * Sets credit card limit.
     *
     * @param newLimit the new limit
     * @throws ServiceException the service exception
     */
    void setCreditCardLimit(BigDecimal newLimit) throws ServiceException;

    /**
     * Sets credit card commission.
     *
     * @param newCommission the new commission
     * @throws ServiceException the service exception
     */
    void setCreditCardCommission(BigDecimal newCommission) throws ServiceException;

    /**
     * Sets debit card percent.
     *
     * @param newPercent the new percent
     * @throws ServiceException the service exception
     */
    void setDebitCardPercent(BigDecimal newPercent) throws ServiceException;

    /**
     * Sets deposit charge strategy.
     *
     * @param strategy the strategy
     * @throws ServiceException the service exception
     */
    void setDepositChargeStrategy(DepositChargeStrategy strategy) throws ServiceException;

    /**
     * Try revert transaction boolean.
     *
     * @param transactionId the transaction id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean tryRevertTransaction(int transactionId) throws ServiceException;

    /**
     * Gets all transactions.
     *
     * @return the all transactions
     * @throws ServiceException the service exception
     */
    Collection<FetchedModel<Transaction>> getAllTransactions() throws ServiceException;
}
