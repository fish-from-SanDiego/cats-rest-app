package org.fishFromSanDiego.lab1.services.implementations;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.abstractions.Observer;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.Transaction;
import org.fishFromSanDiego.lab1.repositories.abstractions.BankRepository;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;

import java.math.BigDecimal;
import java.util.Collection;

public class ConcreteBankService implements BankService {
    private final BankRepository _bankRepository;

    public ConcreteBankService(BankRepository bankRepository) {
        _bankRepository = bankRepository;
    }

    @Override
    public void notifySubscribers(String s) {

    }

    @Override
    public void subscribe(Observer<String> subscriber) {

    }

    @Override
    public void registerNewClient(Client newClient) {


    }


    @Override
    public void chargeAllPercents() {

    }

    @Override
    public void payAllPercents() {

    }

    @Override
    public void takeAllCommissions() {

    }

    @Override
    public void setSuspiciousClientDepositLimit(BigDecimal newLimit) {

    }

    @Override
    public void setSuspiciousClientWithdrawalLimit(BigDecimal newLimit) {

    }

    @Override
    public void setCreditCardLimit(BigDecimal newLimit) {

    }

    @Override
    public void setCreditCardCommission(BigDecimal newCommission) {

    }

    @Override
    public void setDebitCardPercent(BigDecimal newPercent) {

    }

    @Override
    public void setDepositChargeStrategy(DepositChargeStrategy strategy) {

    }

    @Override
    public void revertTransaction(int transactionId) {

    }

    @Override
    public Collection<FetchedModel<Transaction>> getAllTransactions() {
        return null;
    }
}
