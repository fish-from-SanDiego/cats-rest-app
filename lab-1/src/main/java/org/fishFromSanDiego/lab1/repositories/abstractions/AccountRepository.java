package org.fishFromSanDiego.lab1.repositories.abstractions;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.Transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

/**
 * The interface Account repository.
 */
public interface AccountRepository {

    /**
     * Find account by id optional.
     *
     * @param accountId the account id
     * @param clientId  the client id
     * @param bankId    the bank id
     * @return the optional
     */
    Optional<FetchedModel<Account>> findAccountById(int accountId, int clientId, int bankId);

    /**
     * Gets all client accounts.
     *
     * @param clientId the client id
     * @param bankId   the bank id
     * @return the all client accounts
     */
    Collection<FetchedModel<Account>> getAllClientAccounts(int clientId, int bankId);

    /**
     * Deposit money.
     *
     * @param accountId     the account id
     * @param bankId        the bank id
     * @param amount        the amount
     * @param canBeReverted the can be reverted
     * @throws RepositoryException the repository exception
     */
    void depositMoney(int accountId, int bankId, BigDecimal amount, boolean canBeReverted) throws RepositoryException;

    /**
     * Withdraw money.
     *
     * @param accountId     the account id
     * @param bankId        the bank id
     * @param amount        the amount
     * @param canBeReverted the can be reverted
     * @throws RepositoryException the repository exception
     */
    void withdrawMoney(int accountId, int bankId, BigDecimal amount, boolean canBeReverted) throws RepositoryException;

    /**
     * Transfer money.
     *
     * @param senderAccountId    the sender account id
     * @param senderBankId       the sender bank id
     * @param recipientAccountId the recipient account id
     * @param recipientBankId    the recipient bank id
     * @param amount             the amount
     * @param canBeReverted      the can be reverted
     * @throws RepositoryException the repository exception
     */
    void transferMoney(int senderAccountId,
                       int senderBankId,
                       int recipientAccountId,
                       int recipientBankId,
                       BigDecimal amount,
                       boolean canBeReverted) throws RepositoryException;

    /**
     * Gets all account transactions.
     *
     * @param accountId the account id
     * @param bankId    the bank id
     * @return the all account transactions
     */
    Collection<FetchedModel<Transaction>> getAllAccountTransactions(int accountId, int bankId);

    /**
     * Gets all bank transactions.
     *
     * @param bankId the bank id
     * @return the all bank transactions
     */
    Collection<FetchedModel<Transaction>> getAllBankTransactions(int bankId);

    /**
     * Try revert transaction boolean.
     *
     * @param transactionId the transaction id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean tryRevertTransaction(int transactionId) throws RepositoryException;

    /**
     * Add new account int.
     *
     * @param bankId  the bank id
     * @param account the account
     * @return the int
     * @throws RepositoryException the repository exception
     */
    int addNewAccount(int bankId, Account account) throws RepositoryException;

    /**
     * Charge all percents.
     *
     * @param bankId                the bank id
     * @param debitCardPercent      the debit card percent
     * @param depositChargeStrategy the deposit charge strategy
     */
    void chargeAllPercents(int bankId, BigDecimal debitCardPercent, DepositChargeStrategy depositChargeStrategy);

    /**
     * Pay all percents.
     *
     * @param bankId the bank id
     */
    void payAllPercents(int bankId);

    /**
     * Take commissions.
     *
     * @param bankId        the bank id
     * @param commissionSum the commission sum
     */
    void takeCommissions(int bankId, BigDecimal commissionSum);

}
