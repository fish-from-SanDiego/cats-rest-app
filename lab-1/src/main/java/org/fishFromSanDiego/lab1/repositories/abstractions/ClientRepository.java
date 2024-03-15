package org.fishFromSanDiego.lab1.repositories.abstractions;

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
     * Add new client.
     *
     * @param client   the client
     * @param password the password
     * @return the id of added client
     */
    int addNewClient(Client client, String password);

    void updateClientAddress(int clientId, String address);

    Optional<String> findPasswordByClientId(int clientId);
}
