package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;

import java.math.BigDecimal;
import java.util.Optional;

public class SetCreditCardLimitScenario extends ScenarioBase{
    private final BankService bankService;

    public SetCreditCardLimitScenario(Scenario previousScenario,
                                              RepositoryContext repositoryContext,
                                              BankService bankService) {
        super(previousScenario,
                "Set credit card limit",
                "Setting credit card limit",
                repositoryContext);
        this.bankService = bankService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<BigDecimal> value;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            value = Input.AskBigDecimal("Enter bank credit card limit: ");
        } while (value.isEmpty());
        try {
            bankService.setCreditCardLimit(value.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Credit card limit is set to%s%n", value.get());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
