package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;

import java.util.Optional;

/**
 * The type View bank info scenario.
 */
public class ViewBankInfoScenario extends ScenarioBase {
    private final BankService bankService;

    /**
     * Instantiates a new View bank info scenario.
     *
     * @param previousScenario  the previous scenario
     * @param repositoryContext the repository context
     * @param bankService       the bank service
     */
    public ViewBankInfoScenario(Scenario previousScenario,
                                RepositoryContext repositoryContext,
                                BankService bankService) {
        super(previousScenario,
                "View bank info",
                "Bank info",
                repositoryContext);
        this.bankService = bankService;
    }

    @Override
    public Optional<Scenario> execute() {
        FetchedModel<Bank> bank;
        try {
            bank = bankService.getBank();
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Bank id: %d%n%s%n", bank.id(), bank.value().toString());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
