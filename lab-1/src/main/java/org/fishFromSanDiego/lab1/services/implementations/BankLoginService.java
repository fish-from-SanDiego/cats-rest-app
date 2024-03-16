package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.repositories.abstractions.BankRepository;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;
import org.fishFromSanDiego.lab1.services.abstractions.LoginService;

import java.util.Optional;

public class BankLoginService implements LoginService<BankService> {

    private final RepositoryContext _repositoryContext;
    private final int _bankId;

    public BankLoginService(RepositoryContext repositoryContext, int bankId) {
        _repositoryContext = repositoryContext;
        _bankId = bankId;
    }

    @Override
    public Optional<BankService> tryLogin(String password) {
        if (_repositoryContext.getBankRepository().findPasswordByBankId(_bankId).isPresent() &&
                _repositoryContext.getBankRepository().findPasswordByBankId(_bankId).get().equals(password))
            return Optional.of(new ConcreteBankService(_repositoryContext, _bankId));
        return Optional.empty();
    }
}
