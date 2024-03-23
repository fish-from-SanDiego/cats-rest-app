package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.AccountService;

import java.math.BigDecimal;
import java.util.Optional;

public class WithdrawMoneyScenario extends ScenarioBase {
    private final AccountService accountService;

    public WithdrawMoneyScenario(Scenario previousScenario,
                                 RepositoryContext repositoryContext,
                                 AccountService accountService) {
        super(previousScenario,
                "Withdraw money",
                "Withdraw money",
                repositoryContext);
        this.accountService = accountService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<BigDecimal> value;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            value = Input.AskBigDecimal("Enter money amount to withdraw: ");
        } while (value.isEmpty());
        try {
            accountService.withdrawMoney(value.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.println("Money withdrawal was successful");
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
