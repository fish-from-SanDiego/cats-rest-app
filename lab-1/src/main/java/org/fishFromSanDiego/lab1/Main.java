package org.fishFromSanDiego.lab1;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.implementations.scenarios.*;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentAccountRepository;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentBankRepository;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentClientRepository;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var repositoryContext = new RepositoryContext(
                new NonPersistentAccountRepository(),
                new NonPersistentBankRepository("12345"),
                new NonPersistentClientRepository()
        );
        ChoiceScenario initialScenario = new ChoiceScenario(
                null,
                "",
                "Choose login option",
                repositoryContext,
                new ArrayList<>(),
                true
        );
        initialScenario
                .AddScenario(new CentralBankLoginScenario(initialScenario, repositoryContext))
                .AddScenario(new BankLoginScenario(initialScenario, repositoryContext))
                .AddScenario(new ClientLoginScenario(initialScenario, repositoryContext))
                .AddScenario(new QuitScenario());
        var runner = new ScenarioRunnerImpl(initialScenario);
        runner.RunScenarios();
    }
}
