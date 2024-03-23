package org.fishFromSanDiego.lab1.repositories.implementations;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.repositories.abstractions.BankRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NonPersistentBankRepository implements BankRepository {

    Map<Integer, Bank> _banks;
    Map<Integer, String> _bankPasswords;
    String _centralBankPassword;

    public NonPersistentBankRepository(String centralBankPassword) {
        this._centralBankPassword = centralBankPassword;
        _banks = new HashMap<>();
        _bankPasswords = new HashMap<>();
    }

    @Override
    public String getCentralBankPassword() {
        return _centralBankPassword;
    }

    @Override
    public Optional<String> findPasswordByBankId(int bankId) {
        return _bankPasswords.containsKey(bankId) ?
                Optional.of(_bankPasswords.get(bankId))
                : Optional.empty();
    }

    @Override
    public Optional<FetchedModel<Bank>> findBankById(int bankId) {
        return _banks.containsKey(bankId) ?
                Optional.of(new FetchedModel<>(_banks.get(bankId), bankId))
                : Optional.empty();
    }

    @Override
    public Collection<FetchedModel<Bank>> getAllBanks() {
        return _banks.entrySet().stream().map(e -> new FetchedModel<>(e.getValue(), e.getKey())).toList();
    }

    @Override
    public int addNewBank(Bank bank, String password) throws RepositoryException {
        _banks.put(_banks.size(), bank);
        _bankPasswords.put(_bankPasswords.size(), password);
        return _banks.size() - 1;
    }

    @Override
    public void setSuspiciousClientDepositLimit(BigDecimal newLimit, int bankId) throws RepositoryException {
        if (!_banks.containsKey(bankId)) throw new RepositoryException("Bank with such id doesn't exist");
        _banks.put(bankId, _banks.get(bankId).directBuilder(Bank.builder()).suspiciousClientDepositLimit(newLimit).build());
    }

    @Override
    public void setSuspiciousClientWithdrawalLimit(BigDecimal newLimit, int bankId) throws RepositoryException {
        if (!_banks.containsKey(bankId)) throw new RepositoryException("Bank with such id doesn't exist");
        _banks.put(bankId, _banks.get(bankId).directBuilder(Bank.builder()).suspiciousClientWithdrawalLimit(newLimit).build());
    }

    @Override
    public void setCreditCardLimit(BigDecimal newLimit, int bankId) throws RepositoryException {
        if (!_banks.containsKey(bankId)) throw new RepositoryException("Bank with such id doesn't exist");
        _banks.put(bankId, _banks.get(bankId).directBuilder(Bank.builder()).creditCardLimit(newLimit).build());
    }

    @Override
    public void setCreditCardCommission(BigDecimal newCommission, int bankId) throws RepositoryException {
        if (!_banks.containsKey(bankId)) throw new RepositoryException("Bank with such id doesn't exist");
        _banks.put(bankId, _banks.get(bankId).directBuilder(Bank.builder()).creditCardCommission(newCommission).build());
    }

    @Override
    public void setDebitCardPercent(BigDecimal newPercent, int bankId) throws RepositoryException {
        if (!_banks.containsKey(bankId)) throw new RepositoryException("Bank with such id doesn't exist");
        _banks.put(bankId, _banks.get(bankId).directBuilder(Bank.builder()).debitCardPercent(newPercent).build());
    }

    @Override
    public void setDepositChargeStrategy(DepositChargeStrategy strategy, int bankId) throws RepositoryException {
        if (!_banks.containsKey(bankId)) throw new RepositoryException("Bank with such id doesn't exist");
        _banks.put(bankId, _banks.get(bankId).directBuilder(Bank.builder()).depositChargeStrategy(strategy).build());
    }


}
