package org.fishFromSanDiego.lab1.repositories.abstractions;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

/**
 * The interface Bank repository.
 */
public interface BankRepository {
    /**
     * Gets central bank password.
     *
     * @return the central bank password
     */
    String getCentralBankPassword();

    /**
     * Find password by bank id optional.
     *
     * @param bankId the bank id
     * @return the optional
     */
    Optional<String> findPasswordByBankId(int bankId);

    /**
     * Find bank by id optional.
     *
     * @param bankId the bank id
     * @return the optional
     */
    Optional<FetchedModel<Bank>> findBankById(int bankId);

    /**
     * Gets all banks.
     *
     * @return the all banks
     */
    Collection<FetchedModel<Bank>> getAllBanks();

    /**
     * Add new bank int.
     *
     * @param bank     the bank
     * @param password the password
     * @return the int
     * @throws RepositoryException the repository exception
     */
    int addNewBank(Bank bank, String password) throws RepositoryException;

    /**
     * Sets suspicious client deposit limit.
     *
     * @param newLimit the new limit
     * @param bankId   the bank id
     * @throws RepositoryException the repository exception
     */
    void setSuspiciousClientDepositLimit(BigDecimal newLimit, int bankId) throws RepositoryException;

    /**
     * Sets suspicious client withdrawal limit.
     *
     * @param newLimit the new limit
     * @param bankId   the bank id
     * @throws RepositoryException the repository exception
     */
    void setSuspiciousClientWithdrawalLimit(BigDecimal newLimit, int bankId) throws RepositoryException;

    /**
     * Sets credit card limit.
     *
     * @param newLimit the new limit
     * @param bankId   the bank id
     * @throws RepositoryException the repository exception
     */
    void setCreditCardLimit(BigDecimal newLimit, int bankId) throws RepositoryException;

    /**
     * Sets credit card commission.
     *
     * @param newCommission the new commission
     * @param bankId        the bank id
     * @throws RepositoryException the repository exception
     */
    void setCreditCardCommission(BigDecimal newCommission, int bankId) throws RepositoryException;

    /**
     * Sets debit card percent.
     *
     * @param newPercent the new percent
     * @param bankId     the bank id
     * @throws RepositoryException the repository exception
     */
    void setDebitCardPercent(BigDecimal newPercent, int bankId) throws RepositoryException;

    /**
     * Sets deposit charge strategy.
     *
     * @param strategy the strategy
     * @param bankId   the bank id
     * @throws RepositoryException the repository exception
     */
    void setDepositChargeStrategy(DepositChargeStrategy strategy, int bankId) throws RepositoryException;
}
