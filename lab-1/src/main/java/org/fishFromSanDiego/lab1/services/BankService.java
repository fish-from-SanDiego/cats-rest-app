package org.fishFromSanDiego.lab1.services;

import org.fishFromSanDiego.lab1.models.Client;

import java.util.Optional;
import java.util.UUID;

public interface BankService {
    boolean tryRegisterNewClient(Client newClient);

    Optional<LoginService<ClientService>> findClientById(UUID id);
}
