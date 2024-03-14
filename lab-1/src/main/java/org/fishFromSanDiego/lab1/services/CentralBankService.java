package org.fishFromSanDiego.lab1.services;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CentralBankService {
    boolean tryLogin(String password);

    boolean tryRegisterNewBank(String name, String password);

    Optional<LoginService<BankService>> findBankServiceByBankId(UUID bankId);

    Collection<BankService> getAllBankServices();

    void subscribeBankService(BankService bankService);

    void notifyBanksAboutCommission();

    void notifyBanksAboutPercentPayment();

    void notifyBanksAboutPercentCharge();
}
