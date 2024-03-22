package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.util.Collection;

public interface ClientService {
    FetchedModel<Client> getClient() throws ServiceException;

    void setAddressInfo(String address) throws ServiceException;

    void setPassportInfo(int passportId) throws ServiceException;

    Collection<FetchedModel<Account>> getAllAccounts();

}
