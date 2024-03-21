package org.fishFromSanDiego.lab1.repositories.implementations;

import lombok.EqualsAndHashCode;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.repositories.abstractions.ClientRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NonPersistentClientRepository implements ClientRepository {
    Map<CompoundKey, Client> _clients;
    Map<CompoundKey, String> _clientPasswords;

    public NonPersistentClientRepository() {
        _clients = new HashMap<>();
        _clientPasswords = new HashMap<>();
    }

    @Override
    public Optional<FetchedModel<Client>> findBankClientById(int clientId, int bankId) {
        var key = new CompoundKey(bankId, clientId);
        if (!_clients.containsKey(key)) return Optional.empty();
        return Optional.of(new FetchedModel<>(_clients.get(key), key.clientId));
    }

    @Override
    public void addNewClient(int bankId, Client client, String password) {
        _clients.put(
                new CompoundKey(bankId, ((int) _clients.entrySet().stream().filter(e -> e.getKey().bankId == bankId).count()))
                , client);
        _clientPasswords.put(
                new CompoundKey(bankId, ((int) _clientPasswords.entrySet().stream().filter(e -> e.getKey().bankId == bankId).count()))
                , password);
    }

    @Override
    public void updateClientAddress(int clientId, int bankId, String address) throws RepositoryException {
        var key = new CompoundKey(bankId, clientId);
        if (!_clients.containsKey(key)) throw new RepositoryException("Client with such id doesn't exist");
        var client = _clients.get(key);
        var builder = Client.builder().withFullName(client.name(), client.surname());
        if (client.passportId().isPresent())
            builder.withPassport(client.passportId().get());
        builder.withAddress(address);
        _clients.put(key, builder.build());
    }

    @Override
    public void updateClientPassportId(int clientId, int bankId, int passportId) throws RepositoryException {
        var key = new CompoundKey(bankId, clientId);
        if (!_clients.containsKey(key)) throw new RepositoryException("Client with such id doesn't exist");
        var client = _clients.get(key);
        var builder = Client.builder().withFullName(client.name(), client.surname()).withAddress(client.address())
                .withPassport(passportId);
        _clients.put(key, builder.build());
    }

    @Override
    public Optional<String> findPasswordByClientId(int clientId, int bankId) {
        var key = new CompoundKey(bankId, clientId);
        if (!_clientPasswords.containsKey(key))
            return Optional.empty();
        return Optional.of(_clientPasswords.get(key));
    }


    private static final record CompoundKey(int bankId, int clientId) {

    }
}
