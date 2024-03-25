package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.*;
import org.fishFromSanDiego.lab1.services.abstractions.AccountService;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

/**
 * The type Concrete client service.
 */
public class ConcreteClientService implements ClientService {
    private final RepositoryContext repositoryContext;
    private final int clientId;
    private final int bankId;

    /**
     * Instantiates a new Concrete client service.
     *
     * @param repositoryContext the repository context
     * @param clientId          the client id
     * @param bankId            the bank id
     */
    public ConcreteClientService(RepositoryContext repositoryContext, int clientId, int bankId) {
        this.repositoryContext = repositoryContext;
        this.clientId = clientId;
        this.bankId = bankId;
    }


    @Override
    public int registerNewAccount(AccountType accountType) throws ServiceException {
        try {
            return repositoryContext.getAccountRepository().addNewAccount(bankId, new Account(BigDecimal.ZERO, accountType, clientId));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public FetchedModel<Client> getClient() throws ServiceException {
        return repositoryContext.getClientRepository().findBankClientById(clientId, bankId).orElseThrow(ServiceException::new);
    }

    @Override
    public void setAddressInfo(String address) throws ServiceException {
        try {
            repositoryContext.getClientRepository().updateClientAddress(clientId, bankId, address);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setPassportInfo(int passportId) throws ServiceException {
        try {
            repositoryContext.getClientRepository().updateClientPassportId(clientId, bankId, passportId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Collection<FetchedModel<Account>> getAllAccounts() {
        return repositoryContext.getAccountRepository().getAllClientAccounts(clientId, bankId);
    }

    @Override
    public Optional<AccountService> findAccountServiceById(int accountId) {
        var account = repositoryContext.getAccountRepository().findAccountById(accountId, clientId, bankId);
        if (account.isEmpty())
            return Optional.empty();
        return Optional.of(new ConcreteAccountService(repositoryContext, bankId, clientId, accountId));
    }
}
