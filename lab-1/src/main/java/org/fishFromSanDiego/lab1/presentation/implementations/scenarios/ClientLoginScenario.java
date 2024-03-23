package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;
import org.fishFromSanDiego.lab1.services.implementations.ClientLoginService;

import java.util.ArrayList;
import java.util.Optional;

public class ClientLoginScenario extends ScenarioBase {
    public ClientLoginScenario(Scenario previousScenario,
                               RepositoryContext repositoryContext) {
        super(previousScenario, "Login as client", "Client login page", repositoryContext);
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<String> passwordOptional = Optional.empty();
        Optional<Integer> bankId = Optional.empty();
        Optional<Integer> clientId = Optional.empty();
        Optional<ClientService> clientService = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            bankId = Input.AskInt("Enter bank id: ");
            if (bankId.isEmpty())
                continue;
            clientId = Input.AskInt("Enter your id: ");
            if (clientId.isEmpty())
                continue;
            passwordOptional = Input.AskString("Enter your password: ");
            if (passwordOptional.isPresent())
                clientService =
                        new ClientLoginService(repositoryContext, bankId.get(), clientId.get()).tryLogin(passwordOptional.get());
        } while (passwordOptional.isEmpty());
        if (clientService.isEmpty())
            return Optional.of(new BackScenario(this));
        ChoiceScenario result = new ChoiceScenario(this, "", "Choose option", repositoryContext,
                new ArrayList<>(),
                true);
        result
                .AddScenario(new ViewClientInfoScenario(result, repositoryContext, clientService.get()))
                .AddScenario(new RegisterAccountScenario(result, repositoryContext, clientService.get()))
                .AddScenario(new SetAddressInfoScenario(result, repositoryContext, clientService.get()))
                .AddScenario(new SetPassportInfoScenario(result, repositoryContext, clientService.get()))
                .AddScenario(new ViewAllAccountsScenario(result, repositoryContext, clientService.get()))
                .AddScenario(new EnterAccountScenario(result, repositoryContext, clientService.get()))
                .AddScenario(new BackScenario(this));
        return Optional.of(result);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
