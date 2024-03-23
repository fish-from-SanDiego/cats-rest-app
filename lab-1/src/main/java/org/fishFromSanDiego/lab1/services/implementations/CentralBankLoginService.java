package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;
import org.fishFromSanDiego.lab1.services.abstractions.LoginService;

import java.util.Optional;

/**
 * The type Central bank login service.
 */
public class CentralBankLoginService implements LoginService<CentralBankService> {
    private RepositoryContext _repositoryContext;

    /**
     * Instantiates a new Central bank login service.
     *
     * @param repositoryContext the repository context
     */
    public CentralBankLoginService(RepositoryContext repositoryContext) {
        _repositoryContext = repositoryContext;
    }

    @Override
    public Optional<CentralBankService> tryLogin(String password) {
        if (_repositoryContext.getBankRepository().getCentralBankPassword().equals(password))
            return Optional.of(new ConcreteCentralBankService(_repositoryContext) {
            });
        return Optional.empty();
    }
}
