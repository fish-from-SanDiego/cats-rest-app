package org.fishFromSanDiego.lab1.repositories.abstractions;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.FetchedModel;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface BankRepository {
    String getCentralBankPassword();

    Optional<String> findPasswordByBankId(int bankId);

    Optional<FetchedModel<Bank>> findBankById(int bankId);

    Collection<FetchedModel<Bank>> getAllBanks();

    void addNewBank(Bank bank, String password) throws RepositoryException;

    void setSuspiciousClientDepositLimit(BigDecimal newLimit, int bankId) throws RepositoryException;

    void setSuspiciousClientWithdrawalLimit(BigDecimal newLimit, int bankId) throws RepositoryException;

    void setCreditCardLimit(BigDecimal newLimit, int bankId) throws RepositoryException;

    void setCreditCardCommission(BigDecimal newCommission, int bankId) throws RepositoryException;

    void setDebitCardPercent(BigDecimal newPercent, int bankId) throws RepositoryException;

    void setDepositChargeStrategy(DepositChargeStrategy strategy, int bankId) throws RepositoryException;
}
