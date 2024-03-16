package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;
import org.fishFromSanDiego.lab1.services.abstractions.LoginService;

import java.util.Optional;

public class ClientLoginService implements LoginService<ClientService> {
    private RepositoryContext _repositoryContext;
    private final int _bankId;
    private final int _clientId;

    public ClientLoginService(RepositoryContext repositoryContext, int bankId, int clientId) {
        this._repositoryContext = repositoryContext;
        this._bankId = bankId;
        this._clientId = clientId;
    }

    @Override
    public Optional<ClientService> tryLogin(String password) {
        if (_repositoryContext.getClientRepository().findPasswordByClientId(_clientId, _bankId).isPresent() &&
                _repositoryContext.getClientRepository().findPasswordByClientId(_clientId, _bankId).get().equals(password))
            return Optional.of(new ConcreteClientService(_repositoryContext, _clientId, _bankId));
        return Optional.empty();
    }
}
