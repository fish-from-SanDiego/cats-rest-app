package org.fishFromSanDiego.lab1.repositories.abstractions;

import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface AccountRepository {

    Optional<FetchedModel<Account>> findAccountById(int accountId, int clientId, int bankId);

    Collection<FetchedModel<Account>> getAllClientAccounts(int clientId, int bankId);

    void depositMoney(int accountId, int bankId, BigDecimal amount) throws RepositoryException;

    void withdrawMoney(int accountId, int bankId, BigDecimal amount) throws RepositoryException;

    void transferMoney(int senderAccountId, int senderBankId, int recipientAccountId, int recipientBankId, BigDecimal amount) throws RepositoryException;

    Collection<FetchedModel<Transaction>> getAllAccountTransactions(int accountId, int bankId);

    void addNewAccount(int bankId, Account account) throws RepositoryException;

}
