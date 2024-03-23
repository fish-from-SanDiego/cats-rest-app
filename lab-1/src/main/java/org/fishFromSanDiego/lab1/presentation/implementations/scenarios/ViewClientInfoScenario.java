package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;

import java.util.Optional;

/**
 * The type View client info scenario.
 */
public class ViewClientInfoScenario extends ScenarioBase {
    private final ClientService clientService;

    /**
     * Instantiates a new View client info scenario.
     *
     * @param previousScenario  the previous scenario
     * @param repositoryContext the repository context
     * @param clientService     the client service
     */
    public ViewClientInfoScenario(Scenario previousScenario,
                                  RepositoryContext repositoryContext,
                                  ClientService clientService) {
        super(previousScenario,
                "View client info",
                "Client info",
                repositoryContext);
        this.clientService = clientService;
    }

    @Override
    public Optional<Scenario> execute() {
        FetchedModel<Client> client;
        try {
            client = clientService.getClient();
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Client id: %d%n%s%n", client.id(), client.value().toString());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
