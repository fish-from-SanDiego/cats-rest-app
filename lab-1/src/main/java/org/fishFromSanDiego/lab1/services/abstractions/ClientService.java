package org.fishFromSanDiego.lab1.services.abstractions;

import org.fishFromSanDiego.lab1.abstractions.Observer;
import org.fishFromSanDiego.lab1.models.Account;

import java.util.Collection;
import java.util.Optional;

public interface ClientService {
    void setAddressInfo(String address);

    void setPassportInfo(int passportId);

    Optional<AccountService> findAccountServiceById(int accountId);

    Collection<Account> getAllAccounts();

    void subscribeObserverToBankNotifications(Observer<String> observer);
}
