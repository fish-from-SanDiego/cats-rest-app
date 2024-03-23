package org.fishFromSanDiego.lab1.repositories.abstractions;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.util.Optional;

/**
 * Client repository interface.
 */
public interface ClientRepository {
    /**
     * Find bank client by id.
     *
     * @param clientId the client id
     * @param bankId   the bank id
     * @return the optional in which id is present if found
     */
    Optional<FetchedModel<Client>> findBankClientById(int clientId, int bankId);


    /**
     * Add new client int.
     *
     * @param bankId   the bank id
     * @param client   the client
     * @param password the password
     * @return the int
     */
    int addNewClient(int bankId, Client client, String password);

    /**
     * Update client address.
     *
     * @param clientId the client id
     * @param bankId   the bank id
     * @param address  the address
     * @throws RepositoryException the repository exception
     */
    void updateClientAddress(int clientId, int bankId, String address) throws RepositoryException;

    /**
     * Update client passport id.
     *
     * @param clientId   the client id
     * @param bankId     the bank id
     * @param passportId the passport id
     * @throws RepositoryException the repository exception
     */
    void updateClientPassportId(int clientId, int bankId, int passportId) throws RepositoryException;

    /**
     * Find password by client id optional.
     *
     * @param clientId the client id
     * @param bankId   the bank id
     * @return the optional
     */
    Optional<String> findPasswordByClientId(int clientId, int bankId);
}
