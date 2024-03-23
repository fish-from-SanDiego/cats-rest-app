package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.*;
import org.fishFromSanDiego.lab1.services.abstractions.AccountService;

import java.math.BigDecimal;
import java.util.Optional;

public class ConcreteAccountService implements AccountService {
    private final RepositoryContext _repositoryContext;
    private final int _bankId;
    private final int _clientId;
    private final int _accountId;

    public ConcreteAccountService(RepositoryContext repositoryContext, int bankId, int clientId, int accountId) {
        _repositoryContext = repositoryContext;
        _bankId = bankId;
        _clientId = clientId;
        _accountId = accountId;
    }

    @Override
    public FetchedModel<Account> getAccount() throws ServiceException {
        return _repositoryContext.getAccountRepository().findAccountById(_accountId, _clientId, _bankId).orElseThrow(ServiceException::new);
    }

    @Override
    public void depositMoney(BigDecimal amount) throws ServiceException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("Value can't be non-positive");
        }
        Optional<FetchedModel<Bank>> bankOptional = _repositoryContext.getBankRepository().findBankById(_bankId);
        Optional<FetchedModel<Client>> clientOptional =
                _repositoryContext.getClientRepository().findBankClientById(_clientId, _bankId);
        if (bankOptional.isEmpty()) {
            throw new ServiceException("Couldn't load bank info");
        }
        if (clientOptional.isEmpty()) {
            throw new ServiceException("Couldn't load client info");
        }
        Bank bank = bankOptional.get().value();
        Client client = clientOptional.get().value();
        if (client.isSuspicious() && amount.compareTo(bank.suspiciousClientWithdrawalLimit()) > 0) {
            throw new ServiceException("Limit for suspicious clients deposit is exceeded");
        }
        try {
            _repositoryContext.getAccountRepository().depositMoney(_accountId, _bankId, amount, true);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void withdrawMoney(BigDecimal amount) throws ServiceException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("Value can't be non-positive");
        }
        Optional<FetchedModel<Bank>> bankOptional = _repositoryContext.getBankRepository().findBankById(_bankId);
        Optional<FetchedModel<Account>> accountOptional =
                _repositoryContext.getAccountRepository().findAccountById(_accountId, _clientId, _bankId);
        Optional<FetchedModel<Client>> clientOptional =
                _repositoryContext.getClientRepository().findBankClientById(_clientId, _bankId);
        if (bankOptional.isEmpty()) {
            throw new ServiceException("Couldn't load bank info");
        }
        if (accountOptional.isEmpty()) {
            throw new ServiceException("Couldn't load account info");
        }
        if (clientOptional.isEmpty()) {
            throw new ServiceException("Couldn't load client info");
        }
        Bank bank = bankOptional.get().value();
        Client client = clientOptional.get().value();
        Account account = accountOptional.get().value();
        if (account.balance().compareTo(amount) < 0
                && !(account.accountType() instanceof AccountType.Credit)
                || account.balance().add(bank.creditCardLimit()).compareTo(amount) < 0) {
            throw new ServiceException("Not enough money to withdraw");
        }

        if (client.isSuspicious() && amount.compareTo(bank.suspiciousClientWithdrawalLimit()) > 0) {
            throw new ServiceException("Limit for suspicious clients withdrawal is exceeded");
        }
        try {
            _repositoryContext.getAccountRepository().withdrawMoney(_accountId, _bankId, amount, true);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void transferMoney(BigDecimal amount, int recipientBankId, int recipientId) throws ServiceException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("Value can't be non-positive");
        }
        Optional<FetchedModel<Bank>> bankOptional = _repositoryContext.getBankRepository().findBankById(_bankId);
        Optional<FetchedModel<Account>> accountOptional =
                _repositoryContext.getAccountRepository().findAccountById(_accountId, _clientId, _bankId);
        Optional<FetchedModel<Client>> clientOptional =
                _repositoryContext.getClientRepository().findBankClientById(_clientId, _bankId);
        if (bankOptional.isEmpty()) {
            throw new ServiceException("Couldn't load bank info");
        }
        if (accountOptional.isEmpty()) {
            throw new ServiceException("Couldn't load account info");
        }
        if (clientOptional.isEmpty()) {
            throw new ServiceException("Couldn't load client info");
        }
        Bank bank = bankOptional.get().value();
        Client client = clientOptional.get().value();
        Account account = accountOptional.get().value();
        if (account.balance().compareTo(amount) < 0
                && !(account.accountType() instanceof AccountType.Credit)
                || account.balance().add(bank.creditCardLimit()).compareTo(amount) < 0) {
            throw new ServiceException("Not enough money to transfer");
        }

        if (client.isSuspicious() && amount.compareTo(bank.suspiciousClientWithdrawalLimit()) > 0) {
            throw new ServiceException("Limit for suspicious clients transfer is exceeded");
        }
        try {
            _repositoryContext.getAccountRepository()
                    .transferMoney(_accountId, _bankId, recipientId, recipientBankId, amount, true);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
