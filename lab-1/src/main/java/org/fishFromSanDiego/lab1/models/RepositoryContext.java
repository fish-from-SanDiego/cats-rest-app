package org.fishFromSanDiego.lab1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.fishFromSanDiego.lab1.repositories.abstractions.AccountRepository;
import org.fishFromSanDiego.lab1.repositories.abstractions.BankRepository;
import org.fishFromSanDiego.lab1.repositories.abstractions.ClientRepository;

@Data
@AllArgsConstructor
public class RepositoryContext {
    AccountRepository accountRepository;
    BankRepository bankRepository;
    ClientRepository clientRepository;
}
