package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;
import org.fishFromSanDiego.lab1.services.implementations.DepositChargeStrategyConstImpl;

import java.math.BigDecimal;
import java.util.Optional;

public class RegisterBankScenario extends ScenarioBase {
    private final CentralBankService centralBankService;

    public RegisterBankScenario(Scenario previousScenario,
                                RepositoryContext repositoryContext,
                                CentralBankService centralBankService) {
        super(previousScenario, "Register new bank", "Bank registration", repositoryContext);
        this.centralBankService = centralBankService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<String> name = Optional.empty();
        Optional<String> password = Optional.empty();
        Optional<BigDecimal> suspiciousDepositSum = Optional.empty();
        Optional<BigDecimal> suspiciousWithdrawalSum = Optional.empty();
        Optional<BigDecimal> debitCardPercent = Optional.empty();
        Optional<BigDecimal> depositCardPercent = Optional.empty();
        Optional<BigDecimal> creditCardLimit = Optional.empty();
        Optional<BigDecimal> creditCardCommission = Optional.empty();
        Bank bank;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            name = Input.AskString("Enter bank name: ");
            if (name.isEmpty())
                continue;
            suspiciousDepositSum = Input.AskBigDecimal("Enter bank suspicious client deposit sum: ");
            if (suspiciousDepositSum.isEmpty())
                continue;
            suspiciousWithdrawalSum = Input.AskBigDecimal("Enter bank suspicious client withdrawal sum: ");
            if (suspiciousWithdrawalSum.isEmpty())
                continue;
            debitCardPercent = Input.AskBigDecimal("Enter bank debit card percent: ");
            if (debitCardPercent.isEmpty())
                continue;
            creditCardLimit = Input.AskBigDecimal("Enter bank credit card limit: ");
            if (creditCardLimit.isEmpty())
                continue;
            creditCardCommission = Input.AskBigDecimal("Enter bank credit card commission: ");
            if (creditCardCommission.isEmpty())
                continue;
            depositCardPercent = Input.AskBigDecimal("Enter bank deposit card percent: ");
            if (depositCardPercent.isEmpty())
                continue;
            password = Input.AskString("Enter bank password: ");

        } while (name.isEmpty() || name.get().isEmpty() || password.isEmpty() || password.get().isEmpty() ||
                depositCardPercent.isEmpty() || debitCardPercent.isEmpty() ||
                suspiciousDepositSum.isEmpty() || suspiciousWithdrawalSum.isEmpty() || creditCardCommission.isEmpty()
                || creditCardLimit.isEmpty());
        bank = Bank.builder()
                .name(name.get())
                .creditCardLimit(creditCardLimit.get())
                .creditCardCommission(creditCardCommission.get())
                .debitCardPercent(debitCardPercent.get())
                .suspiciousClientDepositLimit(suspiciousDepositSum.get())
                .suspiciousClientWithdrawalLimit(suspiciousWithdrawalSum.get())
                .depositChargeStrategy(new DepositChargeStrategyConstImpl(depositCardPercent.get()))
                .build();
        int bankId;
        try {
            bankId = centralBankService.registerNewBank(bank, password.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Bank with id %d registered successfully%n", bankId);
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
