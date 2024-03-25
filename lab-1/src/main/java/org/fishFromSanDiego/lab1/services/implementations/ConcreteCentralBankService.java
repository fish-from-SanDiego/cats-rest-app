package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;

import java.math.BigDecimal;

/**
 * The type Concrete central bank service.
 */
public class ConcreteCentralBankService implements CentralBankService {
    private final RepositoryContext _repositoryContext;

    /**
     * Instantiates a new Concrete central bank service.
     *
     * @param repositoryContext the repository context
     */
    public ConcreteCentralBankService(RepositoryContext repositoryContext) {

        _repositoryContext = repositoryContext;
    }

    @Override
    public int registerNewBank(Bank bank, String password) throws ServiceException {
        if (bank.creditCardLimit().compareTo(BigDecimal.ZERO) <= 0 ||
                bank.creditCardCommission().compareTo(BigDecimal.ZERO) <= 0 ||
                bank.debitCardPercent().compareTo(BigDecimal.ZERO) <= 0)
            throw new ServiceException("Incorrect bank info");
        try {
            return _repositoryContext.getBankRepository().addNewBank(bank, password);
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
