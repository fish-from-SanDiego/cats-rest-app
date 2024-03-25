package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Set credit card commission scenario.
 */
public class SetCreditCardCommissionScenario extends ScenarioBase {
    private final BankService bankService;

    /**
     * Instantiates a new Set credit card commission scenario.
     *
     * @param previousScenario  the previous scenario
     * @param repositoryContext the repository context
     * @param bankService       the bank service
     */
    public SetCreditCardCommissionScenario(Scenario previousScenario,
                                           RepositoryContext repositoryContext,
                                           BankService bankService) {
        super(previousScenario,
                "Set credit card commission",
                "Setting credit card commission",
                repositoryContext);
        this.bankService = bankService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<BigDecimal> value;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            value = Input.AskBigDecimal("Enter bank credit card commission: ");
        } while (value.isEmpty());
        try {
            bankService.setCreditCardCommission(value.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Credit card commission is set to %s%n", value.get());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
