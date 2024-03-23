package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;
import org.fishFromSanDiego.lab1.services.implementations.DepositChargeStrategyConstImpl;

import java.math.BigDecimal;
import java.util.Optional;

public class SetDepositCardPercentScenario extends ScenarioBase {
    private final BankService bankService;

    public SetDepositCardPercentScenario(Scenario previousScenario,
                                         RepositoryContext repositoryContext,
                                         BankService bankService) {
        super(previousScenario,
                "Set deposit card percent",
                "Setting deposit card percent",
                repositoryContext);
        this.bankService = bankService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<BigDecimal> value;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            value = Input.AskBigDecimal("Enter bank deposit card percent: ");
        } while (value.isEmpty());
        try {
            bankService.setDepositChargeStrategy(new DepositChargeStrategyConstImpl(value.get()));
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Deposit card percent is set to %s%n", value.get());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
