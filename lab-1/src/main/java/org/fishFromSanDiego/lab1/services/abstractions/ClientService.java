package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.AccountType;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.util.Collection;
import java.util.Optional;

/**
 * The interface Client service.
 */
public interface ClientService {
    /**
     * Register new account int.
     *
     * @param accountType the account type
     * @return the int
     * @throws ServiceException the service exception
     */
    int registerNewAccount(AccountType accountType) throws ServiceException;

    /**
     * Gets client.
     *
     * @return the client
     * @throws ServiceException the service exception
     */
    FetchedModel<Client> getClient() throws ServiceException;

    /**
     * Sets address info.
     *
     * @param address the address
     * @throws ServiceException the service exception
     */
    void setAddressInfo(String address) throws ServiceException;

    /**
     * Sets passport info.
     *
     * @param passportId the passport id
     * @throws ServiceException the service exception
     */
    void setPassportInfo(int passportId) throws ServiceException;

    /**
     * Gets all accounts.
     *
     * @return the all accounts
     * @throws ServiceException the service exception
     */
    Collection<FetchedModel<Account>> getAllAccounts() throws ServiceException;

    /**
     * Find account service by id optional.
     *
     * @param accountId the account id
     * @return the optional
     */
    Optional<AccountService> findAccountServiceById(int accountId);

}
