package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.repositories.abstractions.BankRepository;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;
import org.fishFromSanDiego.lab1.services.abstractions.LoginService;

import java.util.Optional;

public class BankLoginService implements LoginService<BankService> {

    private final BankRepository _bankRepository;
    private final int _bankId;

    public BankLoginService(BankRepository bankRepository, int bankId) {
        _bankRepository = bankRepository;
        _bankId = bankId;
    }

    @Override
    public Optional<BankService> tryLogin(String password) {
        if (_bankRepository.findPasswordByBankId(_bankId).isPresent() &&
                _bankRepository.findPasswordByBankId(_bankId).get().equals(password))
            return Optional.of(new ConcreteBankService(_bankRepository));
        return Optional.empty();
    }
}
