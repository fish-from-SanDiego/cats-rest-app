package org.fishFromSanDiego.lab1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.fishFromSanDiego.lab1.repositories.abstractions.AccountRepository;
import org.fishFromSanDiego.lab1.repositories.abstractions.BankRepository;
import org.fishFromSanDiego.lab1.repositories.abstractions.ClientRepository;

/**
 * The type Repository context.
 */
@Data
@AllArgsConstructor
public class RepositoryContext {
    /**
     * The Account repository.
     */
    AccountRepository accountRepository;
    /**
     * The Bank repository.
     */
    BankRepository bankRepository;
    /**
     * The Client repository.
     */
    ClientRepository clientRepository;
}
