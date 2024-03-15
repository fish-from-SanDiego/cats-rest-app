package org.fishFromSanDiego.lab1.services;

import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.abstractions.Publisher;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.TransactionInfo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface BankService extends Publisher<String> {
    boolean tryRegisterNewClient(Client newClient);

    Optional<LoginService<ClientService>> findClientById(int id);

    void chargeAllPercents();

    void payAllPercents();

    void setSuspiciousClientDepositLimit(BigDecimal newLimit);

    void setSuspiciousClientWithdrawalLimit(BigDecimal newLimit);

    void setCreditCardLimit(BigDecimal newLimit);

    void setCreditCardCommission(BigDecimal newCommission);

    void setDebitCardPercent(BigDecimal newPercent);

    void setDepositChargeStrategy(DepositChargeStrategy strategy);

    void revertTransaction(int transactionId);

    Collection<FetchedModel<TransactionInfo>> getAllTransactions();
}
