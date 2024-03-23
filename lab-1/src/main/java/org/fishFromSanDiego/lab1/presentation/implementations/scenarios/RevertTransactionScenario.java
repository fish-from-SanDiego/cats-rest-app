package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;

import java.util.Optional;

public class RevertTransactionScenario extends ScenarioBase {
    private final BankService bankService;

    public RevertTransactionScenario(Scenario previousScenario,
                                     RepositoryContext repositoryContext,
                                     BankService bankService) {
        super(previousScenario,
                "Revert transaction",
                "Reverting transaction",
                repositoryContext);
        this.bankService = bankService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<Integer> value;
        boolean reverted;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            value = Input.AskInt("Enter transaction id: ");
        } while (value.isEmpty());
        try {
            reverted = bankService.tryRevertTransaction(value.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        if (reverted)
            System.out.printf("Transaction with id %s is successfully reverted%n", value.get());
        else
            System.out.println("Transaction can't be reverted");
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
