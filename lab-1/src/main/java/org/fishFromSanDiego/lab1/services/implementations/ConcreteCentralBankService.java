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

    public ConcreteCentralBankService(RepositoryContext repositoryContext) {

        _repositoryContext = repositoryContext;
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
    public void notifyBanksAboutCommission() throws ServiceException {
        for (var bf : _repositoryContext.getBankRepository().getAllBanks()) {
            new ConcreteBankService(_repositoryContext, bf.id()).takeAllCommissions();
        }
    }

    @Override
    public void notifyBanksAboutPercentPayment() throws ServiceException {
        for (var bf : _repositoryContext.getBankRepository().getAllBanks()) {
            new ConcreteBankService(_repositoryContext, bf.id()).payAllPercents();
        }
    }

    @Override
    public void notifyBanksAboutPercentCharge() throws ServiceException {
        for (var bf : _repositoryContext.getBankRepository().getAllBanks()) {
            new ConcreteBankService(_repositoryContext, bf.id()).chargeAllPercents();
        }
    }
}
