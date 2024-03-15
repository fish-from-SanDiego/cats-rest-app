package org.fishFromSanDiego.lab1.services;

import org.fishFromSanDiego.lab1.abstractions.Observer;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    void depositMoney(BigDecimal amount);

    void withdrawMoney(BigDecimal amount);

    void transferMoney(BigDecimal amount, int recipientId);

    void subscribeObserverToBankNotifications(Observer<String> observer);
}
