package org.fishFromSanDiego.lab1.repositories;

import org.fishFromSanDiego.lab1.models.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    Optional<Client> findBankClientById(UUID clientId, UUID bankId);
}
