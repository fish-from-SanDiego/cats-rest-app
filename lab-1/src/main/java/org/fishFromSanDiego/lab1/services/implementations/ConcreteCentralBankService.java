package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;
import org.fishFromSanDiego.lab1.services.abstractions.LoginService;

import java.util.*;

public class ConcreteCentralBankService implements CentralBankService {
    private final RepositoryContext _repositoryContext;
    Collection<BankService> _subscribers;

    public ConcreteCentralBankService(RepositoryContext repositoryContext) {

        _repositoryContext = repositoryContext;
        _subscribers = new ArrayList<>();
    }

    @Override
    public void registerNewBank(Bank bank, String password) throws ServiceException {
        try {
            _repositoryContext.getBankRepository().addNewBank(bank, password);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void subscribeBankService(BankService bankService) {
        _subscribers.add(bankService);
    }

    @Override
    public void notifyBanksAboutCommission() throws ServiceException {
        for (var s : _subscribers) {
            s.takeAllCommissions();
        }
    }

    @Override
    public void notifyBanksAboutPercentPayment() throws ServiceException {
        for (var s : _subscribers) {
            s.payAllPercents();
        }
    }

    @Override
    public void notifyBanksAboutPercentCharge() throws ServiceException {
        for (var s : _subscribers) {
            s.chargeAllPercents();
        }
    }
}
