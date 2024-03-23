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

public interface BankService extends Publisher<String> {
    int registerNewClient(Client newClient, String password) throws ServiceException;

    FetchedModel<Bank> getBank() throws ServiceException;

    void chargeAllPercents() throws ServiceException;

    void payAllPercents() throws ServiceException;

    void takeAllCommissions() throws ServiceException;

    void setSuspiciousClientDepositLimit(BigDecimal newLimit) throws ServiceException;

    void setSuspiciousClientWithdrawalLimit(BigDecimal newLimit) throws ServiceException;

    void setCreditCardLimit(BigDecimal newLimit) throws ServiceException;

    void setCreditCardCommission(BigDecimal newCommission) throws ServiceException;

    void setDebitCardPercent(BigDecimal newPercent) throws ServiceException;

    void setDepositChargeStrategy(DepositChargeStrategy strategy) throws ServiceException;

    boolean tryRevertTransaction(int transactionId) throws ServiceException;

    Collection<FetchedModel<Transaction>> getAllTransactions() throws ServiceException;
}
