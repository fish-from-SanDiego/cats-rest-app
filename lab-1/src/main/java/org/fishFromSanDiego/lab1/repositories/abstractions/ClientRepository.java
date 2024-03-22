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


    void addNewClient(int bankId, Client client, String password);

    void updateClientAddress(int clientId, int bankId, String address) throws RepositoryException;

    void updateClientPassportId(int clientId, int bankId, int passportId) throws RepositoryException;

    Optional<String> findPasswordByClientId(int clientId, int bankId);
}
