package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;

public interface CentralBankService {

    void registerNewBank(Bank bank, String password) throws ServiceException;

    void notifyBanksAboutCommission() throws ServiceException;

    void notifyBanksAboutPercentPayment() throws ServiceException;

    void notifyBanksAboutPercentCharge() throws ServiceException;
}
