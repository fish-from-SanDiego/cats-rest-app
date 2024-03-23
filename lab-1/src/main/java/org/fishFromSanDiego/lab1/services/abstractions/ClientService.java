package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.AccountType;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.util.Collection;
import java.util.Optional;

public interface ClientService {
    int registerNewAccount(AccountType accountType) throws ServiceException;

    FetchedModel<Client> getClient() throws ServiceException;

    void setAddressInfo(String address) throws ServiceException;

    void setPassportInfo(int passportId) throws ServiceException;

    Collection<FetchedModel<Account>> getAllAccounts() throws ServiceException;

    Optional<AccountService> findAccountServiceById(int accountId);

}
