package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;

import java.util.Optional;

public class SetPassportInfoScenario extends ScenarioBase {
    private final ClientService clientService;

    public SetPassportInfoScenario(Scenario previousScenario,
                                   RepositoryContext repositoryContext,
                                   ClientService clientService) {
        super(previousScenario,
                "Set passport info",
                "Setting passport info",
                repositoryContext);
        this.clientService = clientService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<Integer> passportId = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            passportId = Input.AskInt("Enter your passport id: ");
        } while (passportId.isEmpty() || passportId.get() < 0);
        try {
            clientService.setPassportInfo(passportId.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.println("Passport info is set successfully");
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
