package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;

import java.util.Collection;
import java.util.Optional;

public class ViewAllAccountsScenario extends ScenarioBase {
    private final ClientService clientService;

    public ViewAllAccountsScenario(Scenario previousScenario,
                                   RepositoryContext repositoryContext,
                                   ClientService clientService) {
        super(previousScenario,
                "View all accounts",
                "Your accounts\nId\tType\tBalance",
                repositoryContext);
        this.clientService = clientService;
    }

    @Override
    public Optional<Scenario> execute() {
        Collection<FetchedModel<Account>> accounts;
        try {
            accounts = clientService.getAllAccounts();
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.flush();
        System.out.println(this.getTitle());
        accounts.forEach(
                account ->
                        System.out.printf(
                                "%d\t%s\t%s%n",
                                account.id(),
                                account.value().accountType().getName(),
                                account.value().balance().toString()));
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
