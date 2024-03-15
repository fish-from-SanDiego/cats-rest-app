package org.fishFromSanDiego.lab1.repositories.abstractions;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.util.Optional;

public interface BankRepository {
    String getCentralBankPassword();

    Optional<String> findPasswordByBankId(int bankId);

    Optional<FetchedModel<Bank>> findBankById(int bankId);

    void addNewBank(Bank bank, String password) throws RepositoryException;
}
