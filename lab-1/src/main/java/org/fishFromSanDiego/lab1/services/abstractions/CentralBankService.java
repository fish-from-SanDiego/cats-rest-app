package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;

/**
 * The interface Central bank service.
 */
public interface CentralBankService {

    /**
     * Register new bank int.
     *
     * @param bank     the bank
     * @param password the password
     * @return the int
     * @throws ServiceException the service exception
     */
    int registerNewBank(Bank bank, String password) throws ServiceException;

    /**
     * Notify banks about commission.
     *
     * @throws ServiceException the service exception
     */
    void notifyBanksAboutCommission() throws ServiceException;

    /**
     * Notify banks about percent payment.
     *
     * @throws ServiceException the service exception
     */
    void notifyBanksAboutPercentPayment() throws ServiceException;

    /**
     * Notify banks about percent charge.
     *
     * @throws ServiceException the service exception
     */
    void notifyBanksAboutPercentCharge() throws ServiceException;
}
