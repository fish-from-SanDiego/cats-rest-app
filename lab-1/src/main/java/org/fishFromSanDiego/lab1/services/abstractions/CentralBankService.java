package org.fishFromSanDiego.lab1.services.abstractions;

import java.util.Collection;
import java.util.Optional;

public interface CentralBankService {
    boolean tryLogin(String password);

    boolean tryRegisterNewBank(String name, String password);

    Optional<LoginService<BankService>> findBankServiceByBankId(int bankId);

    Collection<BankService> getAllBankServices();

    void subscribeBankService(BankService bankService);

    void notifyBanksAboutCommission();

    void notifyBanksAboutPercentPayment();

    void notifyBanksAboutPercentCharge();
}
