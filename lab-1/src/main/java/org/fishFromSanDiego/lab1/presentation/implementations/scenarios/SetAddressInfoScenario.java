package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;

import java.util.Optional;

public class SetAddressInfoScenario extends ScenarioBase {
    private final ClientService clientService;

    public SetAddressInfoScenario(Scenario previousScenario,
                                  RepositoryContext repositoryContext,
                                  ClientService clientService) {
        super(previousScenario,
                "Set address info",
                "Setting address info",
                repositoryContext);
        this.clientService = clientService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<String> address;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            address = Input.AskString("Enter your address: ");
        } while (address.isEmpty() || address.get().isEmpty());
        try {
            clientService.setAddressInfo(address.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.println("Address info is set successfully");
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
