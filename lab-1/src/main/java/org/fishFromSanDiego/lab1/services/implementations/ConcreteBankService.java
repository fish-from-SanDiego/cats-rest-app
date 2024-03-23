package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.abstractions.Observer;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.*;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The type Concrete bank service.
 */
public class ConcreteBankService implements BankService {
    private final RepositoryContext _repositoryContext;
    private final int _bankId;

    private final Collection<Observer<String>> _subscribers;

    /**
     * Instantiates a new Concrete bank service.
     *
     * @param repositoryContext the repository context
     * @param bankId            the bank id
     */
    public ConcreteBankService(RepositoryContext repositoryContext, int bankId) {
        _repositoryContext = repositoryContext;
        _bankId = bankId;
        _subscribers = new ArrayList<>();
    }

    @Override
    public void notifySubscribers(String s) {
        for (var subscriber : _subscribers) {
            subscriber.accept(s);
        }
    }

    @Override
    public void subscribe(Observer<String> subscriber) {
        _subscribers.add(subscriber);
    }

    @Override
    public int registerNewClient(Client newClient, String password) throws ServiceException {
        return _repositoryContext.getClientRepository().addNewClient(_bankId, newClient, password);
    }

    @Override
    public FetchedModel<Bank> getBank() throws ServiceException {
        return _repositoryContext.getBankRepository().findBankById(_bankId).orElseThrow(ServiceException::new);
    }


    @Override
    public void chargeAllPercents() throws ServiceException {
        var bank = _repositoryContext.getBankRepository().findBankById(_bankId);
        if (bank.isEmpty()) throw new ServiceException("Such bank doesn't exist");
        _repositoryContext.getAccountRepository()
                .chargeAllPercents(_bankId, bank.get().value().debitCardPercent(), bank.get().value().depositChargeStrategy());
    }

    @Override
    public void payAllPercents() throws ServiceException {
        _repositoryContext.getAccountRepository()
                .payAllPercents(_bankId);
    }

    @Override
    public void takeAllCommissions() throws ServiceException {
        var bank = _repositoryContext.getBankRepository().findBankById(_bankId);
        if (bank.isEmpty()) throw new ServiceException("Such bank doesn't exist");
        _repositoryContext.getAccountRepository().takeCommissions(_bankId, bank.get().value().creditCardCommission());
    }

    @Override
    public void setSuspiciousClientDepositLimit(BigDecimal newLimit) throws ServiceException {

        try {
            _repositoryContext.getBankRepository().setSuspiciousClientDepositLimit(newLimit, _bankId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setSuspiciousClientWithdrawalLimit(BigDecimal newLimit) throws ServiceException {

        try {
            _repositoryContext.getBankRepository().setSuspiciousClientWithdrawalLimit(newLimit, _bankId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCreditCardLimit(BigDecimal newLimit) throws ServiceException {

        try {
            _repositoryContext.getBankRepository().setCreditCardLimit(newLimit, _bankId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCreditCardCommission(BigDecimal newCommission) throws ServiceException {

        try {
            _repositoryContext.getBankRepository().setCreditCardCommission(newCommission, _bankId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setDebitCardPercent(BigDecimal newPercent) throws ServiceException {

        try {
            _repositoryContext.getBankRepository().setDebitCardPercent(newPercent, _bankId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setDepositChargeStrategy(DepositChargeStrategy strategy) throws ServiceException {

        try {
            _repositoryContext.getBankRepository().setDepositChargeStrategy(strategy, _bankId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean tryRevertTransaction(int transactionId) throws ServiceException {
        try {
            return _repositoryContext.getAccountRepository().tryRevertTransaction(transactionId);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public Collection<FetchedModel<Transaction>> getAllTransactions() {
        return _repositoryContext.getAccountRepository().getAllBankTransactions(_bankId);
    }
}
