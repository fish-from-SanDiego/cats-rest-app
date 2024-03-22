package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.abstractions.Observer;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;

import java.util.ArrayList;
import java.util.Collection;

public class ConcreteClientService implements ClientService {
    private final RepositoryContext repositoryContext;
    private final int clientId;
    private final int bankId;

    public ConcreteClientService(RepositoryContext repositoryContext, int clientId, int bankId) {
        this.repositoryContext = repositoryContext;
        this.clientId = clientId;
        this.bankId = bankId;
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
            throw new ServiceException(e);
        }
    }

    @Override
    public void setPassportInfo(int passportId) throws ServiceException {
        try {
            repositoryContext.getClientRepository().updateClientPassportId(clientId, bankId, passportId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<FetchedModel<Account>> getAllAccounts() {
        return repositoryContext.getAccountRepository().getAllClientAccounts(clientId, bankId);
    }
}
