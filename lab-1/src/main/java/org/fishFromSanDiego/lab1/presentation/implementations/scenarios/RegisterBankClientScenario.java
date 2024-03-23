package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.models.clientBuilders.ClientFinalBuilder;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;

import java.util.Optional;

public class RegisterBankClientScenario extends ScenarioBase {
    private final BankService bankService;

    public RegisterBankClientScenario(Scenario previousScenario,
                                      RepositoryContext repositoryContext,
                                      BankService bankService) {
        super(previousScenario, "Register new client", "Client registration", repositoryContext);
        this.bankService = bankService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<String> name = Optional.empty();
        Optional<String> password = Optional.empty();
        Optional<String> surname = Optional.empty();
        Optional<String> address = Optional.empty();
        Optional<Integer> passportId = Optional.empty();
        Client client;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            name = Input.AskString("Enter client name: ");
            if (name.isEmpty())
                continue;
            surname = Input.AskString("Enter client surname: ");
            if (surname.isEmpty())
                continue;
            address = Input.AskString("Enter client address (press Enter to omit): ");
            if (address.isEmpty())
                continue;
            passportId = Input.AskInt("Enter client passport id (enter -1 to omit): ");
            password = Input.AskString("Enter client password: ");
        } while (name.isEmpty() || name.get().isEmpty() || surname.isEmpty() || surname.get().isEmpty()
                || address.isEmpty() || passportId.isEmpty() || password.isEmpty() || password.get().isEmpty());
        ClientFinalBuilder builder = Client.builder().withFullName(name.get(), surname.get());
        if (!address.get().isEmpty())
            builder.withAddress(address.get());
        if (passportId.get() != -1)
            builder.withPassport(passportId.get());
        client = builder.build();
        int clientId;
        try {
            clientId = bankService.registerNewClient(client, password.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Client with id %d registered successfully%n", clientId);
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
