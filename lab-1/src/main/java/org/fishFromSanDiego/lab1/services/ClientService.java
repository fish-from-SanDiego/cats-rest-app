package org.fishFromSanDiego.lab1.services;

import java.util.Optional;

public interface ClientService {
    void setAddressInfo(String address);

    void setPassportInfo(int passportId);

    Optional<AccountService> findAccountServiceById(int accountId);
}
