package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;
import org.fishFromSanDiego.lab1.services.abstractions.LoginService;

import java.util.Collection;
import java.util.Optional;

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
    public Collection<BankService> getAllBankServices() {
        return null;
    }

    @Override
    public void subscribeBankService(BankService bankService) {

    }

    @Override
    public void notifyBanksAboutCommission() {

    }

    @Override
    public void notifyBanksAboutPercentPayment() {

    }

    @Override
    public void notifyBanksAboutPercentCharge() {

    }
}
